package de.sambalmueslie.open.col.app.data.terrain.api

import de.sambalmueslie.open.col.app.common.BusinessObjectChangeRequest
import de.sambalmueslie.open.col.app.data.production.api.ProductionChainChangeRequest

data class TerrainProductionChangeRequest(
    val forested: Double,
    val woodless: Double,
    val chain: ProductionChainChangeRequest
) : BusinessObjectChangeRequest
