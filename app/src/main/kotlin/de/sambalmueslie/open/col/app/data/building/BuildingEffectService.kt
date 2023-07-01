package de.sambalmueslie.open.col.app.data.building


import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.data.building.api.BuildingEffect
import de.sambalmueslie.open.col.app.data.building.api.BuildingEffectChangeRequest
import de.sambalmueslie.open.col.app.data.building.api.EffectItemChangeRequest
import de.sambalmueslie.open.col.app.data.building.db.BuildingData
import de.sambalmueslie.open.col.app.data.building.db.EffectItemRepository
import de.sambalmueslie.open.col.app.data.building.db.EffectItemData
import de.sambalmueslie.open.col.app.data.item.ItemService
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class BuildingEffectService(
    private val repository: EffectItemRepository,
    private val itemsService: ItemService,
    private val timeProvider: TimeProvider,
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BuildingEffectService::class.java)
    }


    internal fun get(data: BuildingData): List<BuildingEffect> {
        val item = repository.findByIdOrNull(data.id) ?: return emptyList()
        return listOf(item.convert())
    }

    fun get(buildingIds: Set<Long>): Map<Long, List<BuildingEffect>> {
        return repository.findByIdIn(buildingIds)
            .groupBy { it.id }
            .mapValues { it.value.map { v -> v.convert() } }
    }

    internal fun create(data: BuildingData, effect: List<BuildingEffectChangeRequest>): List<BuildingEffect> {
        return effect.map { create(data, it) }
    }

    private fun create(data: BuildingData, request: BuildingEffectChangeRequest): BuildingEffect {
        return when (request) {
            is EffectItemChangeRequest -> create(data, request)
            else -> throw InvalidRequestException("Unknown type of building effect ${request.javaClass.simpleName}")
        }
    }

    private fun create(
        data: BuildingData,
        request: EffectItemChangeRequest
    ): BuildingEffect {
        val item = itemsService.findByName(request.name)
            ?: throw InvalidRequestException("Cannot find item ${request.name}")
        return repository.save(EffectItemData.create(data, item, request, timeProvider.now())).convert()
    }

}
