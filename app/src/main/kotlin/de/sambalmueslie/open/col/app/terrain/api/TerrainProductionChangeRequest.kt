package de.sambalmueslie.open.col.app.terrain.api

data class TerrainProductionChangeRequest(
    val resource: String,
    val forested: Double,
    val woodless: Double,
)
