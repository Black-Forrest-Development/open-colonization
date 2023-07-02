package de.sambalmueslie.open.col.app.data.terrain.api

import de.sambalmueslie.open.col.app.common.BusinessObject

data class Terrain(
    override val id: Long,
    val name: String,

    val production: List<TerrainProduction>
) : BusinessObject<Long>
