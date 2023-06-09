package de.sambalmueslie.open.col.app.data.tile.api

data class TerrainTile(
    override val coordinate: Coordinate,
    val terrainId: Long
) : Tile
