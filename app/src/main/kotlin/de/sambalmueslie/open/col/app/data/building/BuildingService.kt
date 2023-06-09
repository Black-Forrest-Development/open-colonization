package de.sambalmueslie.open.col.app.data.building


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.BaseCrudService
import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.data.building.api.*
import de.sambalmueslie.open.col.app.data.building.db.BuildingData
import de.sambalmueslie.open.col.app.data.building.db.BuildingRepository
import de.sambalmueslie.open.col.app.data.world.api.World
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

@Singleton
class BuildingService(
    private val repository: BuildingRepository,
    private val requirementService: BuildingRequirementService,
    private val costService: BuildingCostService,
    private val effectService: BuildingEffectService,
    private val timeProvider: TimeProvider,
    cacheService: CacheService,
) : BaseCrudService<Long, Building, BuildingChangeRequest>(logger) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BuildingService::class.java)
        private const val WORLD_REFERENCE = "world"
        private const val CACHE_SIZE = 100L
    }

    private val cache: LoadingCache<Long, Building> = cacheService.register(Building::class) {
        Caffeine.newBuilder()
            .maximumSize(CACHE_SIZE)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .recordStats()
            .build { id -> repository.findByIdOrNull(id)?.let { convert(it) } }
    }

    override fun get(id: Long): Building? {
        return cache.get(id)
    }

    private fun convert(data: BuildingData): Building {
        return data.convert(requirementService.get(data), costService.get(data), effectService.get(data))
    }


    fun findByName(name: String): Building? {
        return repository.findByName(name)?.let { convert(it) }
    }

    override fun getAll(pageable: Pageable): Page<Building> {
        val data = repository.findAll(pageable)
        val bIds = data.content.map { it.id }.toSet()

        val requirements = requirementService.get(bIds)
        val costs = costService.get(bIds)
        val effect = effectService.get(bIds)


        return data.map { it.convert(
            requirements[it.id] ?: emptyList(),
            costs[it.id] ?: emptyList(),
            effect[it.id] ?: emptyList(),
        ) }
    }

    fun create(world: World, request: BuildingChangeRequest): Building {
        return create(request, mapOf(Pair(WORLD_REFERENCE, world)))
    }

    override fun create(request: BuildingChangeRequest, properties: Map<String, Any>): Building {
        val world = properties[WORLD_REFERENCE] as? World ?: throw InvalidRequestException("Cannot find world")
        val data = repository.save(BuildingData.create(world, request, timeProvider.now()))

        val requirements = requirementService.create(data, request.requirements)
        val costs = costService.create(data, request.costs)
        val effect = effectService.create(data, request.effect)

        val result = data.convert(requirements, costs, effect)
        notifyCreated(result)
        return result
    }

    override fun update(id: Long, request: BuildingChangeRequest): Building {
        val data = repository.findByIdOrNull(id) ?: throw InvalidRequestException("Cannot find terrain by $id")
        val result = convert(repository.update(data.update(request, timeProvider.now())))
        cache.invalidate(result.id)
        notifyUpdated(result)
        return result
    }


    fun delete(world: World) {
        val sequence = PageableSequence() { repository.findByWorldId(world.id, it) }
        sequence.forEach { delete(it) }
    }

    override fun delete(id: Long): Building? {
        val data = repository.findByIdOrNull(id) ?: return null
        return delete(data)
    }

    fun delete(data: BuildingData): Building {
        val result = convert(data)
        notifyDeleted(result)
        repository.delete(data)
        cache.invalidate(result.id)
        return result
    }


}
