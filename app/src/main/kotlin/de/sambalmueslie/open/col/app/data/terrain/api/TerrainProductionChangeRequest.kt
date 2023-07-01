package de.sambalmueslie.open.col.app.data.terrain.api

data class TerrainProductionChangeRequest(
    val item: String,
    val forested: Double,
    val woodless: Double,
)
