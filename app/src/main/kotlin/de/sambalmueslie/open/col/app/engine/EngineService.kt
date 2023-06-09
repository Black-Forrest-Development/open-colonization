package de.sambalmueslie.open.col.app.engine


import de.sambalmueslie.open.col.app.common.PageableIterator
import de.sambalmueslie.open.col.app.engine.api.ComponentSystem
import de.sambalmueslie.open.col.app.engine.api.EngineContext
import de.sambalmueslie.open.col.app.data.world.WorldService
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.system.measureTimeMillis

@Singleton
class EngineService(
    private val systems: List<ComponentSystem>,
    private val worldService: WorldService
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EngineService::class.java)
    }

    @Scheduled(cron = "0/30 * * * * *")
    fun update() {
        val timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val worlds = PageableIterator() { worldService.getAll(it) }
        worlds.forEach { updateWorld(EngineContext(timestamp, it)) }
    }

    private fun updateWorld(context: EngineContext) {
        logger.info("[${context.world.id}|${context.timestamp}] World start update")
        val duration = measureTimeMillis {
            systems.forEach {
                val duration = measureTimeMillis {
                    it.update(context)
                }
                handleSystemExecution(it, context, duration)
            }
        }
        handleWorldExecution(context, duration)
    }


    private fun handleSystemExecution(system: ComponentSystem, context: EngineContext, duration: Long) {
        logger.info("[${context.world.id}|${context.timestamp}] - System ${system.javaClass.simpleName} took $duration ms.")
    }


    private fun handleWorldExecution(context: EngineContext, duration: Long) {
        logger.info("[${context.world.id}|${context.timestamp}] World finished after $duration ms.")
    }
}
