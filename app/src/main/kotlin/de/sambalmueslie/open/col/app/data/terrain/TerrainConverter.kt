package de.sambalmueslie.open.col.app.data.terrain


import de.sambalmueslie.open.col.app.common.DataObjectConverter
import de.sambalmueslie.open.col.app.data.terrain.api.Terrain
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainProduction
import de.sambalmueslie.open.col.app.data.terrain.db.TerrainData
import io.micronaut.data.model.Page
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class TerrainConverter(
    private val productionService: TerrainProductionService
) : DataObjectConverter<Terrain, TerrainData> {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TerrainConverter::class.java)
    }

    override fun convert(obj: TerrainData): Terrain {
        val production = productionService.getByTerrain(obj)
        return Terrain(obj.id, obj.name, production)
    }

    override fun convert(objs: List<TerrainData>): List<Terrain> {
        val terrainIds = objs.map { it.id }.toSet()
        val production = productionService.getByTerrainIn(terrainIds).groupBy { it.terrainId }
        return objs.map { convert(it, production) }
    }

    override fun convert(page: Page<TerrainData>): Page<Terrain> {
        val terrainIds = page.content.map { it.id }.toSet()
        val production = productionService.getByTerrainIn(terrainIds).groupBy { it.terrainId }
        return page.map { convert(it, production) }
    }

    private fun convert(data: TerrainData, production: Map<Long, List<TerrainProduction>>): Terrain {
        return Terrain(
            data.id,
            data.name,
            production[data.id] ?: emptyList()
        )
    }
}
