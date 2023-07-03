package de.sambalmueslie.open.col.app.data.terrain


import de.sambalmueslie.open.col.app.common.DataObjectConverter
import de.sambalmueslie.open.col.app.data.production.ProductionChainService
import de.sambalmueslie.open.col.app.data.production.api.ProductionChain
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainProduction
import de.sambalmueslie.open.col.app.data.terrain.db.TerrainProductionData
import io.micronaut.data.model.Page
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class TerrainProductionConverter(
    private val chainService: ProductionChainService
) : DataObjectConverter<TerrainProduction, TerrainProductionData> {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TerrainProductionConverter::class.java)
    }

    override fun convert(obj: TerrainProductionData): TerrainProduction {
        return TerrainProduction(
            obj.id,
            obj.terrainId,
            obj.forested,
            obj.woodless,
            chainService.get(obj.chainId) ?: ProductionChain(-1, emptyList(), emptyList())
        )
    }

    override fun convert(objs: List<TerrainProductionData>): List<TerrainProduction> {
        val chainIds = objs.map { it.chainId }.toSet()
        val chains = chainService.getByIds(chainIds).associateBy { it.id }
        return objs.map { convert(it, chains) }
    }

    override fun convert(page: Page<TerrainProductionData>): Page<TerrainProduction> {
        val chainIds = page.content.map { it.chainId }.toSet()
        val chains = chainService.getByIds(chainIds).associateBy { it.id }
        return page.map { convert(it, chains) }
    }

    private fun convert(data: TerrainProductionData, chains: Map<Long, ProductionChain>): TerrainProduction {
        return TerrainProduction(
            data.id,
            data.terrainId,
            data.forested,
            data.woodless,
            chains[data.chainId] ?: ProductionChain(-1, emptyList(), emptyList())
        )
    }
}
