package de.sambalmueslie.open.col.app.terrain.api

import de.sambalmueslie.open.col.app.common.BusinessObjectChangeRequest

data class TerrainChangeRequest(
    val name: String,

    val production: List<TerrainProductionChangeRequest>
) : BusinessObjectChangeRequest
