package de.sambalmueslie.open.col.app.engine


import de.sambalmueslie.open.col.app.engine.api.ComponentSystem
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.ZoneOffset

@Singleton
class EngineService(
    private val systems: List<ComponentSystem>
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EngineService::class.java)
    }

    @Scheduled(cron = "0/30 * * * * *")
    fun update() {
        val timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        logger.info("[$timestamp] Run engine")
        systems.forEach { it.update(timestamp) }
    }
}
