package de.sambalmueslie.open.col.app.data.settlement.api

import de.sambalmueslie.open.col.app.data.tile.api.Coordinate

data class Settlement(
    val id: Long,
    val coordinate: Coordinate,
    val name: String,
    val worldId: Long,
    val ownerId: Long,
)
