package de.sambalmueslie.open.col.app.data.building


import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.data.building.api.BuildingCost
import de.sambalmueslie.open.col.app.data.building.api.BuildingCostChangeRequest
import de.sambalmueslie.open.col.app.data.building.api.CostResourcesChangeRequest
import de.sambalmueslie.open.col.app.data.building.db.BuildingData
import de.sambalmueslie.open.col.app.data.building.db.CostResourcesData
import de.sambalmueslie.open.col.app.data.building.db.CostResourcesRepository
import de.sambalmueslie.open.col.app.data.resource.ResourceService
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class BuildingCostService(
    private val resourcesRepository: CostResourcesRepository,
    private val resourceService: ResourceService,
    private val timeProvider: TimeProvider,
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BuildingCostService::class.java)
    }

    internal fun get(data: BuildingData): List<BuildingCost> {
        val resources = resourcesRepository.findByIdOrNull(data.id) ?: return emptyList()
        return listOf(resources.convert())
    }


    fun get(buildingIds: Set<Long>): Map<Long, List<BuildingCost>> {
        return resourcesRepository.findByIdIn(buildingIds)
            .groupBy { it.id }
            .mapValues { it.value.map { v -> v.convert() } }
    }

    internal fun create(data: BuildingData, costs: List<BuildingCostChangeRequest>): List<BuildingCost> {
        return costs.map { create(data, it) }
    }

    private fun create(data: BuildingData, request: BuildingCostChangeRequest): BuildingCost {
        return when (request) {
            is CostResourcesChangeRequest -> create(data, request)
            else -> throw InvalidRequestException("Unknown type of building cost ${request.javaClass.simpleName}")
        }
    }

    private fun create(
        data: BuildingData,
        request: CostResourcesChangeRequest
    ): BuildingCost {
        val resource = resourceService.findByName(request.name)
            ?: throw InvalidRequestException("Cannot find resource ${request.name}")
        return resourcesRepository.save(CostResourcesData.create(data, resource, request, timeProvider.now())).convert()
    }
}
