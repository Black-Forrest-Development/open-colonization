package de.sambalmueslie.open.col.app.data.settlement


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.data.player.PlayerService
import de.sambalmueslie.open.col.app.data.player.api.Player
import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import de.sambalmueslie.open.col.app.data.settlement.api.SettlementChangeRequest
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementData
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementRepository
import de.sambalmueslie.open.col.app.data.world.WorldService
import de.sambalmueslie.open.col.app.data.world.api.World
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class SettlementService(
    private val repository: SettlementRepository,
    private val playerService: PlayerService,
    private val worldService: WorldService,
    private val timeProvider: TimeProvider,
    cacheService: CacheService,
) : GenericCrudService<Long, Settlement, SettlementChangeRequest, SettlementData>(
    repository,
    cacheService,
    Settlement::class,
    logger
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SettlementService::class.java)
        private const val PLAYER_REFERENCE = "player"
        private const val WORLD_REFERENCE = "world"
    }

    fun create(playerId: Long, request: SettlementChangeRequest): Settlement {
        val player = playerService.get(playerId)
            ?: throw InvalidRequestException("Cannot find player $playerId")
        val world = worldService.get(player.worldId)
            ?: throw InvalidRequestException("Cannot find world ${player.worldId}")
        val properties = mapOf(
            Pair(PLAYER_REFERENCE, player),
            Pair(WORLD_REFERENCE, world)
        )
        return create(request, properties)
    }

    override fun createData(request: SettlementChangeRequest, properties: Map<String, Any>): SettlementData {
        val player = properties[PLAYER_REFERENCE] as? Player ?: throw InvalidRequestException("Cannot find player")
        val world = properties[WORLD_REFERENCE] as? World ?: throw InvalidRequestException("Cannot find world")
        return SettlementData.create(world, player, request, timeProvider.now())
    }


    fun findByWorld(world: World, pageable: Pageable): Page<Settlement> {
        return repository.findByWorldId(world.id, pageable).map { it.convert() }
    }


    fun findByName(name: String): Settlement? {
        return repository.findByName(name)?.convert()
    }


    override fun isValid(request: SettlementChangeRequest) {
        if (request.name.isBlank()) throw InvalidRequestException("Name cannot be blank")
        if (repository.findByCoordinate(request.coordinate) != null) throw InvalidRequestException("Settlement is already existing")
    }

    override fun updateData(data: SettlementData, request: SettlementChangeRequest): SettlementData {
        return data.update(request, timeProvider.now())
    }


}
