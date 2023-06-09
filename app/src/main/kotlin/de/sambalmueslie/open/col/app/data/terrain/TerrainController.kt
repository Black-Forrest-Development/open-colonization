package de.sambalmueslie.open.col.app.data.terrain


import de.sambalmueslie.open.col.app.data.terrain.api.Terrain
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainAPI
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/terrain")
@Tag(name = "Terrain API")
class TerrainController(
    private val service: TerrainService
) : TerrainAPI {

    @Get("/{id}")
    override fun get(id: Long): Terrain? {
        return service.get(id)
    }

    @Get("/find/by/name")
    override fun findByName(@QueryValue name: String): Terrain? {
        return service.findByName(name)
    }

    @Get()
    override fun getAll(pageable: Pageable): Page<Terrain> {
        return service.getAll(pageable)
    }
}
