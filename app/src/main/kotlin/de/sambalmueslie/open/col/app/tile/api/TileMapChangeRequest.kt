package de.sambalmueslie.open.col.app.tile.api

import de.sambalmueslie.open.col.app.common.BusinessObjectChangeRequest

data class TileMapChangeRequest(
    val width: Int,
    val height: Int,
) : BusinessObjectChangeRequest
