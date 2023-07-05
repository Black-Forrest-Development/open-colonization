package de.sambalmueslie.open.col.app.engine.system


import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.data.building.BuildingService
import de.sambalmueslie.open.col.app.data.building.api.Building
import de.sambalmueslie.open.col.app.data.building.api.BuildingProduction
import de.sambalmueslie.open.col.app.data.item.api.Item
import de.sambalmueslie.open.col.app.data.settlement.SettlementBuildingService
import de.sambalmueslie.open.col.app.data.settlement.SettlementService
import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementBuildingEntry
import de.sambalmueslie.open.col.app.data.terrain.TerrainService
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainProduction
import de.sambalmueslie.open.col.app.data.tile.TileMapService
import de.sambalmueslie.open.col.app.data.tile.api.TerrainTile
import de.sambalmueslie.open.col.app.engine.api.ComponentSystem
import de.sambalmueslie.open.col.app.engine.api.EngineContext
import de.sambalmueslie.open.col.app.engine.service.StorageService
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ProductionChainSystem(
    private val settlementService: SettlementService,
    private val tileMapService: TileMapService,
    private val terrainService: TerrainService,
    private val settlementBuildingService: SettlementBuildingService,
    private val buildingService: BuildingService,
    private val storageService: StorageService,
) : ComponentSystem {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ProductionChainSystem::class.java)
    }

    override fun update(context: EngineContext) {
        val settlements = PageableSequence() { settlementService.findByWorld(context.world, it) }
        settlements.forEach { updateSettlement(context, it) }
    }

    private fun updateSettlement(context: EngineContext, settlement: Settlement) {
        logger.debug("[${settlement.id}] Update settlement ${settlement.name}")
        val data = mutableMapOf<Item, Double>()
        updateTerrainProduction(context, settlement, data)
        updateBuildingProduction(context, settlement, data)

        logger.info(
            "[${settlement.name}] - Production ${
                data.filter { it.value > 0 }.entries.joinToString { "${it.key.name}:${it.value}" }
            }"
        )

        storageService.store(settlement, data)
    }


    private fun updateTerrainProduction(
        context: EngineContext,
        settlement: Settlement,
        data: MutableMap<Item, Double>
    ) {
        val coordinate = settlement.coordinate

        val terrain = tileMapService.getTerrainTile(context.world, coordinate) ?: return
        calcProduction(terrain, data)
    }


    private fun calcProduction(
        tile: TerrainTile,
        result: MutableMap<Item, Double>
    ) {
        val terrain = terrainService.get(tile.terrainId)
            ?: return logger.error("Cannot find terrain for tile ${tile.coordinate}")
        terrain.production.forEach { calcTerrainProduction(tile, it, result) }
    }

    private fun calcTerrainProduction(
        tile: TerrainTile,
        production: TerrainProduction,
        result: MutableMap<Item, Double>
    ) {
        val amount = production.woodless
        if (amount <= 0) return

        // TODO consider source too

        val deliver = production.chain.deliver
        if (deliver.isEmpty()) return

        deliver.forEach {
            logger.info("[${tile.coordinate}] - Produce $amount of ${it.name}")

            val current = result[it] ?: 0.0
            result[it] = amount + current
        }
    }

    private fun updateBuildingProduction(
        context: EngineContext,
        settlement: Settlement,
        result: MutableMap<Item, Double>
    ) {
        val buildings = settlementBuildingService.getBuildings(settlement)
        buildings.forEach { calcProduction( it, result) }
    }

    private fun calcProduction(
        entry: SettlementBuildingEntry,
        result: MutableMap<Item, Double>
    ) {
        if (entry.level <= 0) return
        val building = buildingService.get(entry.id.buildingId) ?: return logger.error("Cannot find building")

        building.production.forEach { calcBuildingProduction(building, entry, it, result) }

    }

    private fun calcBuildingProduction(
        building: Building,
        entry: SettlementBuildingEntry,
        production: BuildingProduction,
        result: MutableMap<Item, Double>
    ) {
        val amount = entry.level * production.factor
        if (amount <= 0) return

        // TODO consider source too

        val deliver = production.chain.deliver
        if (deliver.isEmpty()) return

        deliver.forEach {
            logger.info("[${building.name}] - Produce $amount of ${it.name}")

            val current = result[it] ?: 0.0
            result[it] = amount + current
        }
    }

}
