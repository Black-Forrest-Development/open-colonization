package de.sambalmueslie.open.col.app.tile


import de.sambalmueslie.open.col.app.tile.api.TerrainTile
import de.sambalmueslie.open.col.app.tile.api.TileLayerType
import de.sambalmueslie.open.col.app.tile.db.TerrainTileRepository
import de.sambalmueslie.open.col.app.tile.db.TileLayerRepository
import de.sambalmueslie.open.col.app.tile.db.TileMapRepository
import de.sambalmueslie.open.col.app.world.api.World
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class TileMapService(
    private val repository: TileMapRepository,
    private val layerRepository: TileLayerRepository,
    private val terrainTileRepository: TerrainTileRepository
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TileMapService::class.java)
    }


    fun getTerrainTiles(world: World, pageable: Pageable): Page<TerrainTile> {
        val map = repository.findByWorldId(world.id) ?: return Page.empty()
        val layer = layerRepository.findOneByMapIdAndType(map.id, TileLayerType.TERRAIN) ?: return Page.empty()
        return terrainTileRepository.findByLayerId(layer.id, pageable).map { it.convert() }
    }


}
