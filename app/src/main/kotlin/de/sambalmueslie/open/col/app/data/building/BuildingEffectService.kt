package de.sambalmueslie.open.col.app.data.building


import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.data.building.api.BuildingEffect
import de.sambalmueslie.open.col.app.data.building.api.BuildingEffectChangeRequest
import de.sambalmueslie.open.col.app.data.building.api.EffectGoodsChangeRequest
import de.sambalmueslie.open.col.app.data.building.db.BuildingData
import de.sambalmueslie.open.col.app.data.building.db.EffectGoodsData
import de.sambalmueslie.open.col.app.data.building.db.EffectGoodsRepository
import de.sambalmueslie.open.col.app.data.goods.GoodsService
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class BuildingEffectService(
    private val goodsRepository: EffectGoodsRepository,
    private val goodsService: GoodsService,
    private val timeProvider: TimeProvider,
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BuildingEffectService::class.java)
    }


    internal fun get(data: BuildingData): List<BuildingEffect> {
        val goods = goodsRepository.findByIdOrNull(data.id) ?: return emptyList()
        return listOf(goods.convert())
    }

    fun get(buildingIds: Set<Long>): Map<Long, List<BuildingEffect>> {
        return goodsRepository.findByIdIn(buildingIds)
            .groupBy { it.id }
            .mapValues { it.value.map { v -> v.convert() } }
    }

    internal fun create(data: BuildingData, effect: List<BuildingEffectChangeRequest>): List<BuildingEffect> {
        return effect.map { create(data, it) }
    }

    private fun create(data: BuildingData, request: BuildingEffectChangeRequest): BuildingEffect {
        return when (request) {
            is EffectGoodsChangeRequest -> create(data, request)
            else -> throw InvalidRequestException("Unknown type of building effect ${request.javaClass.simpleName}")
        }
    }

    private fun create(
        data: BuildingData,
        request: EffectGoodsChangeRequest
    ): BuildingEffect {
        val goods = goodsService.findByName(request.name)
            ?: throw InvalidRequestException("Cannot find goods ${request.name}")
        return goodsRepository.save(EffectGoodsData.create(data, goods, request, timeProvider.now())).convert()
    }

}
