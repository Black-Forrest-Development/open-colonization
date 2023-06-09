package de.sambalmueslie.open.col.app.engine.api

import de.sambalmueslie.open.col.app.data.world.api.World

data class EngineContext(
    val timestamp: Long,
    val world: World
)
