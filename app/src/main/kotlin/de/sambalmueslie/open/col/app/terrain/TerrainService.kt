package de.sambalmueslie.open.col.app.terrain


import com.fasterxml.jackson.databind.ObjectMapper
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.BaseCrudService
import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.engine.api.ResourceProduction
import de.sambalmueslie.open.col.app.resource.ResourceService
import de.sambalmueslie.open.col.app.terrain.api.Terrain
import de.sambalmueslie.open.col.app.terrain.api.TerrainChangeRequest
import de.sambalmueslie.open.col.app.terrain.db.TerrainData
import de.sambalmueslie.open.col.app.terrain.db.TerrainProductionData
import de.sambalmueslie.open.col.app.terrain.db.TerrainProductionRepository
import de.sambalmueslie.open.col.app.terrain.db.TerrainRepository
import de.sambalmueslie.open.col.app.world.api.World
import de.sambalmueslie.openbooking.error.InvalidRequestException
import io.micronaut.core.io.ResourceLoader
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

@Singleton
class TerrainService(
    private val loader: ResourceLoader,
    private val mapper: ObjectMapper,

    private val resourceService: ResourceService,

    private val repository: TerrainRepository,
    private val productionRepository: TerrainProductionRepository,
    cacheService: CacheService,
) : BaseCrudService<Long, Terrain, TerrainChangeRequest>(logger) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TerrainService::class.java)
        private const val WORLD_REFERENCE = "world"
    }

    private val cache: LoadingCache<Long, Terrain> = cacheService.register(Terrain::class) {
        Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .recordStats()
            .build { id -> repository.findByIdOrNull(id)?.let { convert(it) } }
    }

    override fun get(id: Long): Terrain? {
        return cache.get(id)
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

    override fun getAll(pageable: Pageable): Page<Terrain> {
        val data = repository.findAll(pageable)
        val tIds = data.content.map { it.id }.toSet()
        val production = productionRepository.findByTerrainIdIn(tIds)
            .groupBy { it.terrainId }
            .mapValues { it.value.map { v -> v.convert() } }

        return data.map { it.convert(production[it.id] ?: emptyList()) }
    }


    fun create(world: World, request: TerrainChangeRequest): Terrain {
        return create(request, mapOf(Pair(WORLD_REFERENCE, world)))
    }

    override fun create(request: TerrainChangeRequest, properties: Map<String, Any>): Terrain {
        val world = properties.get(WORLD_REFERENCE) as? World ?: throw InvalidRequestException("Cannot find world")
        val data = repository.save(TerrainData.create(world, request))
        val production = productionRepository.saveAll(
            request.production.mapNotNull {
                val r = resourceService.findByName(it.resource) ?: return@mapNotNull null
                TerrainProductionData.create(data, r, it)
            }
        ).map { it.convert() }
        val result = data.convert(production)
        notifyCreated(result)
        return result
    }

    override fun update(id: Long, request: TerrainChangeRequest): Terrain {
        val data = repository.findByIdOrNull(id) ?: throw InvalidRequestException("Cannot find terrain by $id")
        val result = convert(repository.update(data.update(request)))
        cache.invalidate(result.id)
        notifyUpdated(result)
        return result
    }

    fun delete(world: World) {
        val sequence = PageableSequence() { repository.findByWorldId(world.id, it) }
        sequence.forEach { delete(it) }
    }

    override fun delete(id: Long): Terrain? {
        val data = repository.findByIdOrNull(id) ?: return null
        return delete(data)
    }

    fun delete(data: TerrainData): Terrain {
        val result = convert(data)
        notifyDeleted(result)
        repository.delete(data)
        cache.invalidate(result.id)
        return result
    }
}
