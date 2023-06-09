package de.sambalmueslie.open.col.app.engine


import de.sambalmueslie.open.col.app.config.EngineConfig
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.TaskScheduler
import jakarta.inject.Named
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class EngineScheduler(
    private val engine: EngineService,
    @param:Named(TaskExecutors.SCHEDULED) private val taskScheduler: TaskScheduler,
    private val config: EngineConfig
) : ApplicationEventListener<ServerStartupEvent> {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EngineScheduler::class.java)
    }

    override fun onApplicationEvent(event: ServerStartupEvent?) {
        taskScheduler.schedule(config.cron) { engine.update() }
    }


}
