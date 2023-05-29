package de.sambalmueslie.open.col.app.tile


import de.sambalmueslie.open.col.app.tile.api.TileMap
import de.sambalmueslie.open.col.app.tile.api.TileMapAPI
import de.sambalmueslie.open.col.app.tile.api.TileMapChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.Controller
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/tile/map")
@Tag(name = "Tile Map API")
class TileMapController(private val service: TileMapService) : TileMapAPI {
    override fun create(worldId: Long, request: TileMapChangeRequest): TileMap? {
        TODO("Not yet implemented")
    }

    override fun get(id: Long): TileMap? {
        TODO("Not yet implemented")
    }

    override fun getAll(pageable: Pageable): Page<TileMap> {
        TODO("Not yet implemented")
    }


}
