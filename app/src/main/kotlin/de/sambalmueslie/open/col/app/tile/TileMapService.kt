package de.sambalmueslie.open.col.app.tile


import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.terrain.TerrainService
import de.sambalmueslie.open.col.app.tile.api.*
import de.sambalmueslie.open.col.app.tile.db.*
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
    private val terrainTileRepository: TerrainTileRepository,
    private val terrainService: TerrainService
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TileMapService::class.java)
    }


    fun getTerrainTiles(world: World, pageable: Pageable): Page<TerrainTile> {
        val map = repository.findByWorldId(world.id) ?: return Page.empty()
        val layer = layerRepository.findOneByMapIdAndType(map.id, TileLayerType.TERRAIN) ?: return Page.empty()
        return terrainTileRepository.findByLayerId(layer.id, pageable).map { it.convert() }
    }

    fun delete(world: World) {
        val map = repository.findByWorldId(world.id) ?: return
        val layer = layerRepository.findByMapId(map.id)
        layer.forEach { terrainTileRepository.deleteByLayerId(it.id) }
        layerRepository.deleteAll(layer)
        repository.delete(map)
    }

    fun create(world: World, request: TileMapChangeRequest): TileMap {
        val map = repository.save(TileMapData.create(world, request))
        layerRepository.saveAll(request.layer.map { TileLayerData.create(map, it) })
        return map.convert()
    }

    fun create(map: TileMap, request: TerrainTileChangeRequest) {
        val layer = layerRepository.findOneByMapIdAndType(map.id, TileLayerType.TERRAIN) ?: return
        val terrain = terrainService.findByName(request.terrain) ?: return
        terrainTileRepository.save(TerrainTileData.create(layer, terrain, request.coordinate))
    }

    fun get(id: Long): TileMap? {
        return repository.findByIdOrNull(id)?.convert()
    }

    fun getAll(pageable: Pageable): Page<TileMap> {
        return repository.findAll(pageable).map { it.convert() }
    }

    fun getTerrainTile(world: World, coordinate: Coordinate): TerrainTile? {
        val map = repository.findByWorldId(world.id) ?: return null
        val layer = layerRepository.findOneByMapIdAndType(map.id, TileLayerType.TERRAIN) ?: return null
        return terrainTileRepository.findByLayerIdAndCoordinate(layer.id, coordinate)?.convert()
    }
}
