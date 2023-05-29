package de.sambalmueslie.open.col.app.world


import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.resource.ResourceService
import de.sambalmueslie.open.col.app.terrain.TerrainService
import de.sambalmueslie.open.col.app.world.api.World
import de.sambalmueslie.open.col.app.world.api.WorldChangeRequest
import de.sambalmueslie.open.col.app.world.db.WorldData
import de.sambalmueslie.open.col.app.world.db.WorldRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class WorldService(
    private val repository: WorldRepository,

    private val resourceService: ResourceService,
    private val terrainService: TerrainService
) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(WorldService::class.java)
    }

    fun create(request: WorldChangeRequest): World {
        logger.info("Create world $request")
        val world = repository.save(WorldData.create(request)).convert()

        resourceService.setup(world)
        terrainService.setup(world)

        return world
    }

    fun get(id: Long): World? {
        return repository.findByIdOrNull(id)?.convert()
    }

    fun findByName(name: String): World? {
        return repository.findByName(name)?.convert()
    }

    fun getAll(pageable: Pageable): Page<World> {
        return repository.findAll(pageable).map { it.convert() }
    }
}
