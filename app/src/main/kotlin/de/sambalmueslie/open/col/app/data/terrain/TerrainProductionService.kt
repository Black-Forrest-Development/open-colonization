package de.sambalmueslie.open.col.app.data.terrain


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.data.production.ProductionChainService
import de.sambalmueslie.open.col.app.data.terrain.api.Terrain
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainProduction
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainProductionChangeRequest
import de.sambalmueslie.open.col.app.data.terrain.db.TerrainData
import de.sambalmueslie.open.col.app.data.terrain.db.TerrainProductionChainRepository
import de.sambalmueslie.open.col.app.data.terrain.db.TerrainProductionData
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class TerrainProductionService(
    private val repository: TerrainProductionChainRepository,
    private val timeProvider: TimeProvider,
    cacheService: CacheService,
    private val converter: TerrainProductionConverter,

    private val chainService: ProductionChainService
) : GenericCrudService<Long, TerrainProduction, TerrainProductionChangeRequest, TerrainProductionData>(
    repository, converter, cacheService, TerrainProduction::class, logger
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TerrainProductionService::class.java)
        private const val TERRAIN_REFERENCE = "terrain"
    }

    fun create(terrain: Terrain, request: TerrainProductionChangeRequest) {
        create(request, mapOf(Pair(TERRAIN_REFERENCE, terrain)))
    }

    override fun createData(
        request: TerrainProductionChangeRequest,
        properties: Map<String, Any>
    ): TerrainProductionData {
        val terrain = properties[TERRAIN_REFERENCE] as? Terrain ?: throw InvalidRequestException("Cannot find terrain")
        val chain = chainService.create(request.chain)
        return TerrainProductionData.create(terrain, request, chain, timeProvider.now())
    }

    override fun updateData(
        data: TerrainProductionData,
        request: TerrainProductionChangeRequest
    ): TerrainProductionData {
        return data.update(request, timeProvider.now())
    }

    override fun isValid(request: TerrainProductionChangeRequest) {
        // intentionally left empty
    }

    fun getByTerrain(obj: TerrainData): List<TerrainProduction> {
        return repository.findByTerrainId(obj.id).map { converter.convert(it) }
    }

    fun getByTerrainIn(ids: Set<Long>): List<TerrainProduction> {
        return repository.findByTerrainIdIn(ids).map { converter.convert(it) }
    }
}
