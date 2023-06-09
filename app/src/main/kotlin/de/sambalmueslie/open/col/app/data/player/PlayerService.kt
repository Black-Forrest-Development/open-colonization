package de.sambalmueslie.open.col.app.data.player


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.data.player.api.Player
import de.sambalmueslie.open.col.app.data.player.api.PlayerChangeRequest
import de.sambalmueslie.open.col.app.data.player.db.PlayerData
import de.sambalmueslie.open.col.app.data.player.db.PlayerRepository
import de.sambalmueslie.open.col.app.data.world.WorldService
import de.sambalmueslie.open.col.app.data.world.api.World
import de.sambalmueslie.openbooking.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class PlayerService(
    private val repository: PlayerRepository,
    private val worldService: WorldService,
    private val timeProvider: TimeProvider,
    cacheService: CacheService,
) : GenericCrudService<Long, Player, PlayerChangeRequest, PlayerData>(
    repository,
    cacheService,
    Player::class,
    logger
) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(PlayerService::class.java)
        private const val WORLD_REFERENCE = "world"
    }

    fun create(worldId: Long, request: PlayerChangeRequest): Player {
        val world = worldService.get(worldId) ?: throw InvalidRequestException("Cannot find world $worldId")
        return create(request, mapOf(Pair(WORLD_REFERENCE, world)))
    }


    fun findByName(name: String): Player? {
        return repository.findByName(name)?.convert()
    }

    override fun createData(request: PlayerChangeRequest, properties: Map<String, Any>): PlayerData {
        val world = properties[WORLD_REFERENCE] as? World ?: throw InvalidRequestException("Cannot find world")
        return PlayerData.create(world, request, timeProvider.now())
    }

    override fun updateData(data: PlayerData, request: PlayerChangeRequest): PlayerData {
        return data.update(request, timeProvider.now())
    }

    override fun isValid(request: PlayerChangeRequest) {
        if (request.name.isBlank()) throw InvalidRequestException("Name cannot be blank")
    }

}
