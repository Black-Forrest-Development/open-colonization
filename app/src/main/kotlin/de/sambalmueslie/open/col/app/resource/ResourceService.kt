package de.sambalmueslie.open.col.app.resource


import com.fasterxml.jackson.databind.ObjectMapper
import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.resource.api.Resource
import de.sambalmueslie.open.col.app.resource.api.ResourceChangeRequest
import de.sambalmueslie.open.col.app.resource.db.ResourceData
import de.sambalmueslie.open.col.app.resource.db.ResourceRepository
import de.sambalmueslie.open.col.app.world.WorldService
import de.sambalmueslie.open.col.app.world.api.World
import de.sambalmueslie.openbooking.error.InvalidRequestException
import io.micronaut.core.io.ResourceLoader
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ResourceService(
    private val repository: ResourceRepository,
    cacheService: CacheService,
) : GenericCrudService<Long, Resource, ResourceChangeRequest, ResourceData>(
    repository,
    cacheService,
    Resource::class,
    logger
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ResourceService::class.java)
    }

    fun create(world: World, request: List<ResourceChangeRequest>) {
        repository.saveAll(request.map { ResourceData.create(world, it) })
            .map { it.convert() }
            .forEach { notifyCreated(it) }
    }


    override fun createData(request: ResourceChangeRequest): ResourceData {
        TODO("Not yet implemented")
    }

    override fun updateData(data: ResourceData, request: ResourceChangeRequest): ResourceData {
        TODO("Not yet implemented")
    }

    override fun isValid(request: ResourceChangeRequest) {
        if (request.name.isBlank()) throw InvalidRequestException("Name cannot be blank")
    }

    fun findByName(name: String): Resource? {
        return repository.findByName(name)?.convert()
    }

    fun delete(world: World) {
        repository.deleteByWorldId(world.id)
    }


}
