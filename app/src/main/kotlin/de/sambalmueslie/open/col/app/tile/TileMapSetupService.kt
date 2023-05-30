package de.sambalmueslie.open.col.app.tile


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.sambalmueslie.open.col.app.common.BusinessObjectChangeListener
import de.sambalmueslie.open.col.app.tile.api.TerrainTileChangeRequest
import de.sambalmueslie.open.col.app.tile.api.TileMap
import de.sambalmueslie.open.col.app.tile.api.TileMapChangeRequest
import de.sambalmueslie.open.col.app.world.WorldService
import de.sambalmueslie.open.col.app.world.api.World
import io.micronaut.context.annotation.Context
import io.micronaut.core.io.ResourceLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Context
class TileMapSetupService(
    private val loader: ResourceLoader,
    private val mapper: ObjectMapper,
    worldService: WorldService,
    private val service: TileMapService
) : BusinessObjectChangeListener<Long, World> {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TileMapSetupService::class.java)
    }

    init {
        worldService.register(this)
    }

    override fun handleCreated(obj: World) {
        setup(obj)
    }

    private fun setup(world: World) {
        logger.info("[${world.id}] Run initial setup")
        service.delete(world)
        val rawData = loader.getResourceAsStream("setup/map.json").get()
        val data: List<TileMapChangeRequest> = mapper.readValue(rawData)
        if (data.isEmpty()) return
        data.forEach {
            val map = service.create(world, it)
            loadTiles(map)
        }
    }

    private fun loadTiles(map: TileMap) {
        val rawData = loader.getResourceAsStream("setup/tiles.json").get()
        val data: List<TerrainTileChangeRequest> = mapper.readValue(rawData)
        if (data.isEmpty()) return
        data.forEach { service.create(map, it) }
    }

}
