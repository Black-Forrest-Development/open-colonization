package de.sambalmueslie.open.col.app.tile.api


data class TerrainTileChangeRequest(
    val coordinate: Coordinate,
    val terrain: String,
)
