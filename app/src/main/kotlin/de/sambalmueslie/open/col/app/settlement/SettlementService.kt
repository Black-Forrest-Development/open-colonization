package de.sambalmueslie.open.col.app.settlement


import de.sambalmueslie.open.col.app.player.PlayerService
import de.sambalmueslie.open.col.app.settlement.api.Settlement
import de.sambalmueslie.open.col.app.settlement.api.SettlementChangeRequest
import de.sambalmueslie.open.col.app.settlement.db.SettlementData
import de.sambalmueslie.open.col.app.settlement.db.SettlementRepository
import de.sambalmueslie.open.col.app.world.WorldService
import de.sambalmueslie.open.col.app.world.api.World
import de.sambalmueslie.openbooking.error.InvalidRequestException
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class SettlementService(
    private val repository: SettlementRepository,
    private val playerService: PlayerService,
    private val worldService: WorldService
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SettlementService::class.java)
    }

    fun create(playerId: Long, request: SettlementChangeRequest): Settlement {
        val player = playerService.get(playerId)
            ?: throw InvalidRequestException("Cannot find player $playerId")
        val world = worldService.get(player.worldId)
            ?: throw InvalidRequestException("Cannot find world ${player.worldId}")

        val existing = repository.findByCoordinate(request.coordinate)
        if (existing != null) return existing.convert()

        val result = repository.save(SettlementData.create(world, player, request))
        return result.convert()
    }

    fun findByWorld(world: World, pageable: Pageable): Page<Settlement> {
        return repository.findByWorldId(world.id, pageable).map { it.convert() }
    }


}
