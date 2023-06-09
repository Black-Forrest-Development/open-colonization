package de.sambalmueslie.open.col.app.data.player.api

import de.sambalmueslie.open.col.app.common.BusinessObject

data class Player(
    override val id: Long,
    val name: String,
    val worldId: Long,
) : BusinessObject<Long>
