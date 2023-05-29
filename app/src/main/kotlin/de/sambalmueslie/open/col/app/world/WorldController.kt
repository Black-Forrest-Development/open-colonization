package de.sambalmueslie.open.col.app.world


import de.sambalmueslie.open.col.app.world.api.World
import de.sambalmueslie.open.col.app.world.api.WorldAPI
import de.sambalmueslie.open.col.app.world.api.WorldChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/world")
@Tag(name = "World API")
class WorldController(private val service: WorldService) : WorldAPI {
    @Post("/setup")
    override fun create(@Body request: WorldChangeRequest): HttpResponse<String> {
        service.create(request)
        return HttpResponse.created("")
    }

    @Get("/{id}")
    override fun get(id: Long): World? {
        return service.get(id)
    }

    @Get("/find/by/name")
    override fun findByName(@QueryValue name: String): World? {
        return service.findByName(name)
    }

    @Get()
    override fun getAll(pageable: Pageable): Page<World> {
        return service.getAll(pageable)
    }
}
