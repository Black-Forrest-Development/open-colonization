package de.sambalmueslie.open.col.app.data.terrain


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.GenericCrudService
import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.data.terrain.api.Terrain
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainChangeRequest
import de.sambalmueslie.open.col.app.data.terrain.db.TerrainData
import de.sambalmueslie.open.col.app.data.terrain.db.TerrainRepository
import de.sambalmueslie.open.col.app.data.world.api.World
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class TerrainService(
    private val repository: TerrainRepository,
    private val timeProvider: TimeProvider,
    cacheService: CacheService,
    private val converter: TerrainConverter,
    private val productionService: TerrainProductionService,
) : GenericCrudService<Long, Terrain, TerrainChangeRequest, TerrainData>(
    repository, converter, cacheService, Terrain::class, logger
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TerrainService::class.java)
        private const val WORLD_REFERENCE = "world"
    }

    fun findByName(name: String): Terrain? {
        return repository.findByName(name)?.let { converter.convert(it) }
    }

    fun create(world: World, request: TerrainChangeRequest): Terrain {
        return create(request, mapOf(Pair(WORLD_REFERENCE, world)))
    }

    override fun createData(request: TerrainChangeRequest, properties: Map<String, Any>): TerrainData {
        val world = properties[WORLD_REFERENCE] as? World ?: throw InvalidRequestException("Cannot find world")
        return TerrainData.create(world, request, timeProvider.now())
    }

    override fun createDependencies(request: TerrainChangeRequest, properties: Map<String, Any>, result: Terrain) {
        request.production.forEach { productionService.create(result, it) }
    }

    override fun updateData(data: TerrainData, request: TerrainChangeRequest): TerrainData {
        return data.update(request, timeProvider.now())
    }

    override fun isValid(request: TerrainChangeRequest) {
        if (request.name.isBlank()) throw InvalidRequestException("Name could not be blank")
    }

    fun delete(world: World) {
        val sequence = PageableSequence() { repository.findByWorldId(world.id, it) }
        sequence.forEach { delete(it) }
    }


}
