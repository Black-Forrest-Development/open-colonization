package de.sambalmueslie.open.col.app.terrain.api

import de.sambalmueslie.open.col.app.common.BusinessObject
import de.sambalmueslie.open.col.app.engine.api.ResourceProduction

data class Terrain(
    override val id: Long,
    val name: String,

    val production: List<ResourceProduction>
) : BusinessObject<Long>
