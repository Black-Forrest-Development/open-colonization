package de.sambalmueslie.open.col.app.engine.service


import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.engine.api.ComponentSystem
import de.sambalmueslie.open.col.app.engine.api.EngineContext
import de.sambalmueslie.open.col.app.engine.api.ResourceProduction
import de.sambalmueslie.open.col.app.resource.ResourceService
import de.sambalmueslie.open.col.app.terrain.TerrainService
import de.sambalmueslie.open.col.app.tile.TileMapService
import de.sambalmueslie.open.col.app.tile.api.TerrainTile
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class TerrainProductionService(
    private val terrainService: TerrainService,
    private val resourceService: ResourceService,
    private val tileMapService: TileMapService
) : ComponentSystem {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TerrainProductionService::class.java)
    }


    override fun update(context: EngineContext) {
        val tiles = PageableSequence() { tileMapService.getTerrainTiles(context.world, it) }
        tiles.forEach { calcProduction(context, it) }
    }

    private fun calcProduction(context: EngineContext, tile: TerrainTile) {
        val terrain = terrainService.get(tile.terrainId)
            ?: return logger.error("Cannot find terrain for tile ${tile.coordinate}")
        terrain.production.forEach { calcTerrainProduction(context, tile, it) }
    }

    private fun calcTerrainProduction(context: EngineContext, tile: TerrainTile, production: ResourceProduction) {
        val resource = resourceService.get(production.resourceId)
            ?: return logger.error("Cannot find resource for tile ${tile.coordinate} - ${production.resourceId}")

        logger.info("[${tile.coordinate}] - Produce ${production.woodless} of ${resource.name}")
    }

}
