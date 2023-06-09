package de.sambalmueslie.open.col.app.data.building.api

import de.sambalmueslie.open.col.app.common.BusinessObjectChangeRequest

data class BuildingChangeRequest(
    val name: String,
    val requirements: List<BuildingRequirementChangeRequest>,
    val costs: List<BuildingCostChangeRequest>,
    val effect: List<BuildingEffectChangeRequest>,
) : BusinessObjectChangeRequest
