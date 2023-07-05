package de.sambalmueslie.open.col.app.data.building.api

import de.sambalmueslie.open.col.app.common.BusinessObjectChangeRequest

data class BuildingChangeRequest(
    val name: String,
    val production: List<BuildingProductionChangeRequest>
) : BusinessObjectChangeRequest
