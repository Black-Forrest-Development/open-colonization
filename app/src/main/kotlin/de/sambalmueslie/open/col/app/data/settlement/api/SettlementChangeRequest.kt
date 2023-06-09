package de.sambalmueslie.open.col.app.data.settlement.api

import de.sambalmueslie.open.col.app.common.BusinessObjectChangeRequest
import de.sambalmueslie.open.col.app.data.tile.api.Coordinate

data class SettlementChangeRequest(
    val coordinate: Coordinate,
    val name: String
) : BusinessObjectChangeRequest
