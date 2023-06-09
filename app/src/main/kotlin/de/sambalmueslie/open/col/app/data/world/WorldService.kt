package de.sambalmueslie.open.col.app.data.world


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.data.world.api.World
import de.sambalmueslie.open.col.app.data.world.api.WorldChangeRequest
import de.sambalmueslie.open.col.app.data.world.db.WorldData
import de.sambalmueslie.open.col.app.data.world.db.WorldRepository
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class WorldService(
    private val repository: WorldRepository,
    private val timeProvider: TimeProvider,
    cacheService: CacheService,
) : GenericCrudService<Long, World, WorldChangeRequest, WorldData>(repository, cacheService, World::class, logger) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(WorldService::class.java)
    }

    override fun createData(request: WorldChangeRequest, properties: Map<String, Any>): WorldData {
        logger.info("Create world $request")
        return WorldData.create(request, timeProvider.now())
    }

    override fun isValid(request: WorldChangeRequest) {
        if (request.name.isBlank()) throw InvalidRequestException("Name cannot be blank")
    }

    override fun updateData(data: WorldData, request: WorldChangeRequest): WorldData {
        return data.update(request, timeProvider.now())
    }

    fun findByName(name: String): World? {
        return repository.findByName(name)?.convert()
    }

}
