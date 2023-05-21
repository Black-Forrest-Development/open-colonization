package de.sambalmueslie.open.col.app.terrain.api

import de.sambalmueslie.open.col.app.engine.api.ResourceProduction

data class Terrain(
    val id: Long,
    val name: String,

    val production: List<ResourceProduction>
)
