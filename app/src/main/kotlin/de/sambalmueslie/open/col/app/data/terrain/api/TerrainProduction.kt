package de.sambalmueslie.open.col.app.data.terrain.api

import de.sambalmueslie.open.col.app.common.BusinessObject
import de.sambalmueslie.open.col.app.data.production.api.ProductionChain

data class TerrainProduction(
    override val id: Long,
    val terrainId: Long,
    val forested: Double,
    val woodless: Double,
    val chain: ProductionChain,
) : BusinessObject<Long>
