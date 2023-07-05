package de.sambalmueslie.open.col.app.data.building


import de.sambalmueslie.open.col.app.common.DataObjectConverter
import de.sambalmueslie.open.col.app.data.building.api.BuildingProduction
import de.sambalmueslie.open.col.app.data.building.db.BuildingProductionData
import de.sambalmueslie.open.col.app.data.production.ProductionChainService
import de.sambalmueslie.open.col.app.data.production.api.ProductionChain
import io.micronaut.data.model.Page
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class BuildingProductionConverter(
    private val chainService: ProductionChainService
) : DataObjectConverter<BuildingProduction, BuildingProductionData> {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BuildingProductionConverter::class.java)
    }

    override fun convert(obj: BuildingProductionData): BuildingProduction {
        return BuildingProduction(
            obj.id,
            obj.buildingId,
            obj.factor,
            chainService.get(obj.chainId) ?: ProductionChain(-1, emptyList(), emptyList())
        )
    }

    override fun convert(objs: List<BuildingProductionData>): List<BuildingProduction> {
        val chainIds = objs.map { it.chainId }.toSet()
        val chains = chainService.getByIds(chainIds).associateBy { it.id }
        return objs.map { convert(it, chains) }
    }

    override fun convert(page: Page<BuildingProductionData>): Page<BuildingProduction> {
        val chainIds = page.content.map { it.chainId }.toSet()
        val chains = chainService.getByIds(chainIds).associateBy { it.id }
        return page.map { convert(it, chains) }
    }

    private fun convert(data: BuildingProductionData, chains: Map<Long, ProductionChain>): BuildingProduction {
        return BuildingProduction(
            data.id,
            data.buildingId,
            data.factor,
            chains[data.chainId] ?: ProductionChain(-1, emptyList(), emptyList())
        )
    }
}
