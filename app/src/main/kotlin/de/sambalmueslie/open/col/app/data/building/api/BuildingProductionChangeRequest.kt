package de.sambalmueslie.open.col.app.data.building.api

import de.sambalmueslie.open.col.app.common.BusinessObjectChangeRequest
import de.sambalmueslie.open.col.app.data.production.api.ProductionChainChangeRequest

data class BuildingProductionChangeRequest(
    val factor: Double,
    val chain: ProductionChainChangeRequest
) : BusinessObjectChangeRequest
