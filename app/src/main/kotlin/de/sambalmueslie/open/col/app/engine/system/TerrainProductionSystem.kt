package de.sambalmueslie.open.col.app.engine.system


import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.data.resource.ResourceService
import de.sambalmueslie.open.col.app.data.resource.api.Resource
import de.sambalmueslie.open.col.app.data.settlement.SettlementService
import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import de.sambalmueslie.open.col.app.data.terrain.TerrainService
import de.sambalmueslie.open.col.app.data.tile.TileMapService
import de.sambalmueslie.open.col.app.data.tile.api.TerrainTile
import de.sambalmueslie.open.col.app.engine.api.ComponentSystem
import de.sambalmueslie.open.col.app.engine.api.EngineContext
import de.sambalmueslie.open.col.app.engine.api.ResourceProduction
import de.sambalmueslie.open.col.app.engine.service.StorageService
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class TerrainProductionSystem(
    private val terrainService: TerrainService,
    private val resourceService: ResourceService,
    private val tileMapService: TileMapService,
    private val settlementService: SettlementService,
    private val storageService: StorageService
) : ComponentSystem {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TerrainProductionSystem::class.java)
    }


    override fun update(context: EngineContext) {
        val settlements = PageableSequence() { settlementService.findByWorld(context.world, it) }
        settlements.forEach { calcProduction(context, it) }
    }

    private fun calcProduction(context: EngineContext, settlement: Settlement) {
        val coordinate = settlement.coordinate
        val data = mutableMapOf<Resource, Double>()
        val terrain = tileMapService.getTerrainTile(context.world, coordinate) ?: return
        calcProduction(terrain, data)

        logger.info(
            "[${settlement.name}] - Production ${
                data.filter { it.value > 0 }.entries.joinToString { "${it.key.name}:${it.value}" }
            }"
        )

        storageService.store(settlement, data)
    }

    private fun calcProduction(
        tile: TerrainTile,
        result: MutableMap<Resource, Double>
    ) {
        val terrain = terrainService.get(tile.terrainId)
            ?: return logger.error("Cannot find terrain for tile ${tile.coordinate}")
        terrain.production.forEach { calcTerrainProduction(tile, it, result) }
    }

    private fun calcTerrainProduction(
        tile: TerrainTile,
        production: ResourceProduction,
        result: MutableMap<Resource, Double>
    ) {
        val amount = production.woodless
        if (amount <= 0) return

        val resource = resourceService.get(production.resourceId)
            ?: return logger.error("Cannot find resource for tile ${tile.coordinate} - ${production.resourceId}")

        logger.info("[${tile.coordinate}] - Produce $amount of ${resource.name}")

        val current = result[resource] ?: 0.0
        result[resource] = amount + current
    }

}