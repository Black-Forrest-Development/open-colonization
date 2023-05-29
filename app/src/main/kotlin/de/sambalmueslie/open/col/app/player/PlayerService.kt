package de.sambalmueslie.open.col.app.player


import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.player.api.Player
import de.sambalmueslie.open.col.app.player.api.PlayerChangeRequest
import de.sambalmueslie.open.col.app.player.db.PlayerData
import de.sambalmueslie.open.col.app.player.db.PlayerRepository
import de.sambalmueslie.open.col.app.world.WorldService
import de.sambalmueslie.openbooking.error.InvalidRequestException
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class PlayerService(
    private val repository: PlayerRepository,

    private val worldService: WorldService,
) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(PlayerService::class.java)
    }

    fun create(worldId: Long, request: PlayerChangeRequest): Player {
        val world = worldService.get(worldId) ?: throw InvalidRequestException("Cannot find world $worldId")

        return repository.save(PlayerData.create(world, request)).convert()
    }

    fun update(id: Long, request: PlayerChangeRequest): Player {
        val data = repository.findByIdOrNull(id) ?: throw InvalidRequestException("Cannot find player $id")
        return repository.update(data.update(request)).convert()
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }

    fun get(id: Long): Player? {
        return repository.findByIdOrNull(id)?.convert()
    }

    fun findByName(name: String): Player? {
        return repository.findByName(name)?.convert()
    }

    fun getAll(pageable: Pageable): Page<Player> {
        return repository.findAll(pageable).map { it.convert() }
    }
}
