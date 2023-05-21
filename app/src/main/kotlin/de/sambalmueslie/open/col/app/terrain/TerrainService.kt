package de.sambalmueslie.open.col.app.terrain


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.engine.api.ResourceProduction
import de.sambalmueslie.open.col.app.resource.ResourceService
import de.sambalmueslie.open.col.app.terrain.api.Terrain
import de.sambalmueslie.open.col.app.terrain.api.TerrainChangeRequest
import de.sambalmueslie.open.col.app.terrain.db.TerrainData
import de.sambalmueslie.open.col.app.terrain.db.TerrainProductionData
import de.sambalmueslie.open.col.app.terrain.db.TerrainProductionRepository
import de.sambalmueslie.open.col.app.terrain.db.TerrainRepository
import io.micronaut.core.io.ResourceLoader
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class TerrainService(
    private val loader: ResourceLoader,
    private val mapper: ObjectMapper,

    private val resourceService: ResourceService,

    private val repository: TerrainRepository,
    private val productionRepository: TerrainProductionRepository,
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TerrainService::class.java)
    }

    fun setup() {
        logger.info("Run initial setup")
        productionRepository.deleteAll()
        repository.deleteAll()
        val rawData = loader.getResourceAsStream("setup/terrain.json").get()
        val data: List<TerrainChangeRequest> = mapper.readValue(rawData)
        if (data.isEmpty()) return

        data.forEach { req ->
            val d = repository.save(TerrainData.create(req))
            productionRepository.saveAll(
                req.production.mapNotNull {
                    val r = resourceService.findByName(it.resource) ?: return@mapNotNull null
                    TerrainProductionData.create(d, r, it)
                }
            )

        }
    }

    fun get(id: Long): Terrain? {
        return repository.findByIdOrNull(id)?.let { convert(it) }
    }

    private fun convert(data: TerrainData): Terrain {
        return data.convert(getProduction(data))
    }

    private fun getProduction(data: TerrainData): List<ResourceProduction> {
        return productionRepository.findByTerrainId(data.id).map { it.convert() }
    }

    fun findByName(name: String): Terrain? {
        return repository.findByName(name)?.let { convert(it) }
    }

    fun getAll(pageable: Pageable): Page<Terrain> {
        val data = repository.findAll(pageable)
        val tIds = data.content.map { it.id }.toSet()
        val production = productionRepository.findByTerrainIdIn(tIds)
            .groupBy { it.terrainId }
            .mapValues { it.value.map { v -> v.convert() } }

        return data.map { it.convert(production[it.id] ?: emptyList()) }
    }


}
