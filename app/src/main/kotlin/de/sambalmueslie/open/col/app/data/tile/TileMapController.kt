package de.sambalmueslie.open.col.app.data.tile


import de.sambalmueslie.open.col.app.data.tile.api.TileMap
import de.sambalmueslie.open.col.app.data.tile.api.TileMapAPI
import de.sambalmueslie.open.col.app.data.tile.api.TileMapChangeRequest
import de.sambalmueslie.open.col.app.data.world.api.World
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/tile/map")
@Tag(name = "Tile Map API")
class TileMapController(private val service: TileMapService) : TileMapAPI {


    @Get("/{id}")
    override fun get(id: Long): TileMap? {
        return service.get(id)
    }


    @Get()
    override fun getAll(pageable: Pageable): Page<TileMap> {
        return service.getAll(pageable)
    }


}
