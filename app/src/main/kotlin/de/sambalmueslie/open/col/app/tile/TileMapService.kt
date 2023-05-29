package de.sambalmueslie.open.col.app.tile


import de.sambalmueslie.open.col.app.tile.db.TileMapRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class TileMapService(
    private val repository: TileMapRepository
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TileMapService::class.java)
    }


}
