package de.sambalmueslie.open.col.app.data.building


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.sambalmueslie.open.col.app.common.BusinessObjectChangeListener
import de.sambalmueslie.open.col.app.data.building.api.BuildingChangeRequest
import de.sambalmueslie.open.col.app.data.world.WorldService
import de.sambalmueslie.open.col.app.data.world.api.World
import io.micronaut.context.annotation.Context
import io.micronaut.core.io.ResourceLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Context
class BuildingSetupService(
    private val loader: ResourceLoader,
    private val mapper: ObjectMapper,
    worldService: WorldService,
    private val service: BuildingService
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BuildingSetupService::class.java)
    }

    init {
        worldService.register(object : BusinessObjectChangeListener<Long, World> {
            override fun handleCreated(obj: World) {
                setup(obj)
            }
        })
    }

    private fun setup(world: World) {
        logger.info("[${world.id}] Run initial setup")
        service.delete(world)
        val rawData = loader.getResourceAsStream("setup/building.json").get()
        val data: List<BuildingChangeRequest> = mapper.readValue(rawData)
        if (data.isEmpty()) return
        data.forEach { service.create(world, it) }
    }
}
