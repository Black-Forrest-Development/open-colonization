package de.sambalmueslie.open.col.app.data.building


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.data.building.api.Building
import de.sambalmueslie.open.col.app.data.building.api.BuildingProduction
import de.sambalmueslie.open.col.app.data.building.api.BuildingProductionChangeRequest
import de.sambalmueslie.open.col.app.data.building.db.BuildingData
import de.sambalmueslie.open.col.app.data.building.db.BuildingProductionData
import de.sambalmueslie.open.col.app.data.building.db.BuildingProductionRepository
import de.sambalmueslie.open.col.app.data.production.ProductionChainService
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class BuildingProductionService(
    private val repository: BuildingProductionRepository,
    private val timeProvider: TimeProvider,
    cacheService: CacheService,
    private val converter: BuildingProductionConverter,

    private val chainService: ProductionChainService
) : GenericCrudService<Long, BuildingProduction, BuildingProductionChangeRequest, BuildingProductionData>(
    repository, converter, cacheService, BuildingProduction::class, logger
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BuildingProductionService::class.java)
        private const val BUILDING_REFERENCE = "building"
    }


    fun create(terrain: Building, request: BuildingProductionChangeRequest) {
        create(request, mapOf(Pair(BUILDING_REFERENCE, terrain)))
    }

    override fun createData(
        request: BuildingProductionChangeRequest,
        properties: Map<String, Any>
    ): BuildingProductionData {
        val building =
            properties[BUILDING_REFERENCE] as? Building ?: throw InvalidRequestException("Cannot find building")
        val chain = chainService.create(request.chain)
        return BuildingProductionData.create(building, request, chain, timeProvider.now())
    }

    override fun updateData(
        data: BuildingProductionData,
        request: BuildingProductionChangeRequest
    ): BuildingProductionData {
        return data.update(request, timeProvider.now())
    }

    override fun isValid(request: BuildingProductionChangeRequest) {
        // intentionally left empty
    }

    fun getByBuilding(obj: BuildingData): List<BuildingProduction> {
        return repository.findByBuildingId(obj.id).map { converter.convert(it) }
    }

    fun getByBuildingIn(ids: Set<Long>): List<BuildingProduction> {
        return repository.findByBuildingIdIn(ids).map { converter.convert(it) }
    }
}
