package de.sambalmueslie.open.col.app.config


import io.micronaut.context.annotation.ConfigurationProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ConfigurationProperties("engine")
class EngineConfig {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EngineConfig::class.java)
    }

    var cron: String = "0/30 * * * * *"
        set(value) {
            logger.debug("Set engine cron from $cron to $value")
            field = value
        }


}
