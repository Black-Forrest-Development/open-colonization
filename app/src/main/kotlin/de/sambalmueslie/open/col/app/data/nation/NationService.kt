package de.sambalmueslie.open.col.app.data.nation


import de.sambalmueslie.open.col.app.cache.CacheService
import de.sambalmueslie.open.col.app.common.TimeProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class NationService(
    private val timeProvider: TimeProvider,
    cacheService: CacheService,
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(NationService::class.java)
    }


}
