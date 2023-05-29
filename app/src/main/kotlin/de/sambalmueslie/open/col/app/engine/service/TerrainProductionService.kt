package de.sambalmueslie.open.col.app.engine.service


import de.sambalmueslie.open.col.app.engine.api.ComponentSystem
import de.sambalmueslie.open.col.app.terrain.db.TerrainProductionRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class TerrainProductionService(
    private val productionRepository: TerrainProductionRepository
) : ComponentSystem {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TerrainProductionService::class.java)
    }

    override fun update(timestamp: Long) {
        TODO("Not yet implemented")
    }


}
