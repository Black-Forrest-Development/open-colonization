package de.sambalmueslie.open.col.app.data.building.api

import de.sambalmueslie.open.col.app.common.BusinessObject
import de.sambalmueslie.open.col.app.data.production.api.ProductionChain

data class BuildingProduction(
    override val id: Long,
    val buildingId: Long,
    val factor: Double,
    val chain: ProductionChain,
) : BusinessObject<Long>
