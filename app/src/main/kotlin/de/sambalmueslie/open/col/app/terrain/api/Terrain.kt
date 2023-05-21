package de.sambalmueslie.open.col.app.terrain.api

data class Terrain(
    val id: Long,
    val x: Int,
    val y: Int,
    val definition: TerrainDefinition
)
