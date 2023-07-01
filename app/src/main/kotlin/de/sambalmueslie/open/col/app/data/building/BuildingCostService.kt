package de.sambalmueslie.open.col.app.data.building


import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.data.building.api.BuildingCost
import de.sambalmueslie.open.col.app.data.building.api.BuildingCostChangeRequest
import de.sambalmueslie.open.col.app.data.building.api.CostItemsChangeRequest
import de.sambalmueslie.open.col.app.data.building.db.BuildingData
import de.sambalmueslie.open.col.app.data.building.db.CostItemsData
import de.sambalmueslie.open.col.app.data.building.db.CostItemsRepository
import de.sambalmueslie.open.col.app.data.item.ItemService
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class BuildingCostService(
    private val repository: CostItemsRepository,
    private val itemService: ItemService,
    private val timeProvider: TimeProvider,
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BuildingCostService::class.java)
    }

    internal fun get(data: BuildingData): List<BuildingCost> {
        val items = repository.findByIdOrNull(data.id) ?: return emptyList()
        return listOf(items.convert())
    }


    fun get(buildingIds: Set<Long>): Map<Long, List<BuildingCost>> {
        return repository.findByIdIn(buildingIds)
            .groupBy { it.id }
            .mapValues { it.value.map { v -> v.convert() } }
    }

    internal fun create(data: BuildingData, costs: List<BuildingCostChangeRequest>): List<BuildingCost> {
        return costs.map { create(data, it) }
    }

    private fun create(data: BuildingData, request: BuildingCostChangeRequest): BuildingCost {
        return when (request) {
            is CostItemsChangeRequest -> create(data, request)
            else -> throw InvalidRequestException("Unknown type of building cost ${request.javaClass.simpleName}")
        }
    }

    private fun create(
        data: BuildingData,
        request: CostItemsChangeRequest
    ): BuildingCost {
        val item = itemService.findByName(request.name)
            ?: throw InvalidRequestException("Cannot find item ${request.name}")
        return repository.save(CostItemsData.create(data, item, request, timeProvider.now())).convert()
    }
}
