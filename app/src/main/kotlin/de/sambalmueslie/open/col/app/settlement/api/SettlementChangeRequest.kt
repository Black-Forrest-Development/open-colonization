package de.sambalmueslie.open.col.app.settlement.api

import de.sambalmueslie.open.col.app.tile.api.Coordinate

data class SettlementChangeRequest(
    val coordinate: Coordinate,
    val name: String
)
