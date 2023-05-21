package de.sambalmueslie.open.col.app.terrain.api

data class TerrainChangeRequest(
    val name: String,

    val production: List<TerrainProductionChangeRequest>
)
