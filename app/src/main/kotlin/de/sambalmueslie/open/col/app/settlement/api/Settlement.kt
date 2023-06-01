package de.sambalmueslie.open.col.app.settlement.api

import de.sambalmueslie.open.col.app.tile.api.Coordinate

data class Settlement(
    val id: Long,
    val coordinate: Coordinate,
    val name: String,
    val worldId: Long,
    val ownerId: Long,
)
