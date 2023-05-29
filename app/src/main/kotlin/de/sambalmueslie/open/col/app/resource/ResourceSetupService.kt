package de.sambalmueslie.open.col.app.resource


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.sambalmueslie.open.col.app.common.BusinessObjectChangeListener
import de.sambalmueslie.open.col.app.resource.api.ResourceChangeRequest
import de.sambalmueslie.open.col.app.world.WorldService
import de.sambalmueslie.open.col.app.world.api.World
import io.micronaut.context.annotation.Context
import io.micronaut.core.io.ResourceLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Context
class ResourceSetupService(
    private val loader: ResourceLoader,
    private val mapper: ObjectMapper,
    worldService: WorldService,
    private val service: ResourceService
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ResourceSetupService::class.java)
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
        val rawData = loader.getResourceAsStream("setup/resources.json").get()
        val data: List<ResourceChangeRequest> = mapper.readValue(rawData)
        if (data.isEmpty()) return
        service.create(world, data)
    }

}
