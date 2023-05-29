package de.sambalmueslie.open.col.app.player


import de.sambalmueslie.open.col.app.player.api.Player
import de.sambalmueslie.open.col.app.player.api.PlayerAPI
import de.sambalmueslie.open.col.app.player.api.PlayerChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/player")
@Tag(name = "Player API")
class PlayerController(private val service: PlayerService) : PlayerAPI {

    @Post("/{worldId}")
    override fun create(worldId: Long, @Body request: PlayerChangeRequest): Player {
        return service.create(worldId, request)
    }

    @Put("/{id}")
    override fun update(id: Long, @Body request: PlayerChangeRequest): Player {
        return service.update(id, request)
    }

    @Delete("/{id}")
    override fun delete(id: Long) {
        service.delete(id)
    }

    @Get("/{id}")
    override fun get(id: Long): Player? {
        return service.get(id)
    }

    @Get("/find/by/name")
    override fun findByName(@QueryValue name: String): Player? {
        return service.findByName(name)
    }

    @Get()
    override fun getAll(pageable: Pageable): Page<Player> {
        return service.getAll(pageable)
    }


}
