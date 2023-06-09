package de.sambalmueslie.open.col.app.data.resource


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.data.resource.api.Resource
import de.sambalmueslie.open.col.app.data.resource.api.ResourceChangeRequest
import de.sambalmueslie.open.col.app.data.resource.db.ResourceData
import de.sambalmueslie.open.col.app.data.resource.db.ResourceRepository
import de.sambalmueslie.open.col.app.data.world.api.World
import de.sambalmueslie.openbooking.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ResourceService(
    private val repository: ResourceRepository,
    cacheService: CacheService,
) : GenericCrudService<Long, Resource, ResourceChangeRequest, ResourceData>(
    repository, cacheService, Resource::class, logger
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ResourceService::class.java)
        private const val WORLD_REFERENCE = "world"
    }


    fun create(world: World, request: ResourceChangeRequest) {
        create(request, mapOf(Pair(WORLD_REFERENCE, world)))
    }


    override fun createData(request: ResourceChangeRequest, properties: Map<String, Any>): ResourceData {
        val world = properties.get(WORLD_REFERENCE) as? World ?: throw InvalidRequestException("Cannot find world")
        return ResourceData.create(world, request)
    }

    override fun updateData(data: ResourceData, request: ResourceChangeRequest): ResourceData {
        return data.update(request)
    }

    override fun isValid(request: ResourceChangeRequest) {
        if (request.name.isBlank()) throw InvalidRequestException("Name cannot be blank")
    }

    fun findByName(name: String): Resource? {
        return repository.findByName(name)?.convert()
    }

    fun delete(world: World) {
        val sequence = PageableSequence() { repository.findByWorldId(world.id, it) }
        sequence.forEach { delete(it) }
    }


}
