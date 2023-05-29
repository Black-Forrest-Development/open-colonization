package de.sambalmueslie.open.col.app.world.api

import de.sambalmueslie.open.col.app.common.BusinessObject

data class World(
    override val id: Long,
    val name: String
) : BusinessObject<Long>
