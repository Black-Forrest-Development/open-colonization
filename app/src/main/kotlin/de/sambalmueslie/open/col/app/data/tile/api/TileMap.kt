package de.sambalmueslie.open.col.app.data.tile.api

import de.sambalmueslie.open.col.app.common.BusinessObject

data class TileMap(
    override val id: Long,
    val width: Int,
    val height: Int,

    val worldId: Long
) : BusinessObject<Long>
