package de.sambalmueslie.open.col.app.world


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.world.api.World
import de.sambalmueslie.open.col.app.world.api.WorldChangeRequest
import de.sambalmueslie.open.col.app.world.db.WorldData
import de.sambalmueslie.open.col.app.world.db.WorldRepository
import de.sambalmueslie.openbooking.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class WorldService(
    private val repository: WorldRepository,
    cacheService: CacheService,
) : GenericCrudService<Long, World, WorldChangeRequest, WorldData>(repository, cacheService, World::class, logger) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(WorldService::class.java)
    }

    override fun createData(request: WorldChangeRequest): WorldData {
        logger.info("Create world $request")
        return WorldData.create(request)
    }

    override fun isValid(request: WorldChangeRequest) {
        if (request.name.isBlank()) throw InvalidRequestException("Name cannot be blank")
    }

    override fun updateData(data: WorldData, request: WorldChangeRequest): WorldData {
        return data.update(request)
    }

    fun findByName(name: String): World? {
        return repository.findByName(name)?.convert()
    }

}
