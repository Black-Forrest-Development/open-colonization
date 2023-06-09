package de.sambalmueslie.open.col.app.data.tile.api

import de.sambalmueslie.open.col.app.common.BusinessObjectChangeRequest

data class TileMapChangeRequest(
    val width: Int,
    val height: Int,
    val layer: List<TileLayerChangeRequest>
) : BusinessObjectChangeRequest
