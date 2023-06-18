package de.sambalmueslie.open.col.app.engine.service


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.sambalmueslie.open.col.app.common.BusinessObjectChangeListener
import de.sambalmueslie.open.col.app.data.building.BuildingService
import de.sambalmueslie.open.col.app.data.building.api.BuildingChangeRequest
import de.sambalmueslie.open.col.app.data.goods.GoodsService
import de.sambalmueslie.open.col.app.data.goods.api.GoodsChangeRequest
import de.sambalmueslie.open.col.app.data.resource.ResourceService
import de.sambalmueslie.open.col.app.data.resource.api.ResourceChangeRequest
import de.sambalmueslie.open.col.app.data.terrain.TerrainService
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainChangeRequest
import de.sambalmueslie.open.col.app.data.tile.TileMapService
import de.sambalmueslie.open.col.app.data.tile.api.TerrainTileChangeRequest
import de.sambalmueslie.open.col.app.data.tile.api.TileMap
import de.sambalmueslie.open.col.app.data.tile.api.TileMapChangeRequest
import de.sambalmueslie.open.col.app.data.world.WorldService
import de.sambalmueslie.open.col.app.data.world.api.World
import io.micronaut.context.annotation.Context
import io.micronaut.core.io.ResourceLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Context
class WorldSetupService(
    private val loader: ResourceLoader,
    private val mapper: ObjectMapper,

    worldService: WorldService,

    private val resourceService: ResourceService,
    private val terrainService: TerrainService,
    private val tileMapService: TileMapService,
    private val goodsService: GoodsService,
    private val buildingService: BuildingService
) : BusinessObjectChangeListener<Long, World> {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(WorldSetupService::class.java)
    }

    init {
        worldService.register(this)
    }

    override fun handleCreated(obj: World) {
        setupWorld(obj)

    }

    private fun setupWorld(world: World) {
        logger.info("[${world.id}] Run initial setup")
        setupResources(world)
        setupTerrain(world)
        setupTileMap(world)

        setupGoods(world)
        setupBuilding(world)
    }


    private fun setupResources(world: World) {
        logger.info("[${world.id}] Run initial setup - setup resources")
        resourceService.delete(world)
        val rawData = loader.getResourceAsStream("setup/resource.json").get()
        val data: List<ResourceChangeRequest> = mapper.readValue(rawData)
        if (data.isEmpty()) return
        data.forEach { resourceService.create(world, it) }
    }

    private fun setupTerrain(world: World) {
        logger.info("[${world.id}] Run initial setup - setup terrain")
        terrainService.delete(world)
        val rawData = loader.getResourceAsStream("setup/terrain.json").get()
        val data: List<TerrainChangeRequest> = mapper.readValue(rawData)
        if (data.isEmpty()) return
        data.forEach { terrainService.create(world, it) }
    }

    private fun setupTileMap(world: World) {
        logger.info("[${world.id}] Run initial setup - setup tile map")
        tileMapService.delete(world)
        val rawData = loader.getResourceAsStream("setup/map.json").get()
        val data: List<TileMapChangeRequest> = mapper.readValue(rawData)
        if (data.isEmpty()) return
        data.forEach {
            val map = tileMapService.create(world, it)
            loadTiles(map)
        }
    }
    private fun loadTiles(map: TileMap) {
        val rawData = loader.getResourceAsStream("setup/tiles.json").get()
        val data: List<TerrainTileChangeRequest> = mapper.readValue(rawData)
        if (data.isEmpty()) return
        data.forEach { tileMapService.create(map, it) }
    }
    private fun setupGoods(world: World) {
        logger.info("[${world.id}] Run initial setup - setup goods")
        goodsService.delete(world)
        val rawData = loader.getResourceAsStream("setup/goods.json").get()
        val data: List<GoodsChangeRequest> = mapper.readValue(rawData)
        if (data.isEmpty()) return
        data.forEach { goodsService.create(world, it) }
    }


    private fun setupBuilding(world: World) {
        logger.info("[${world.id}] Run initial setup - setup building")
        buildingService.delete(world)
        val rawData = loader.getResourceAsStream("setup/building.json").get()
        val data: List<BuildingChangeRequest> = mapper.readValue(rawData)
        if (data.isEmpty()) return
        data.forEach { buildingService.create(world, it) }
    }

}
