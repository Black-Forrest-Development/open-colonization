package de.sambalmueslie.open.col.app.data.building.api

data class CostResourcesChangeRequest(
    val name: String,
    val amount: Int
) :  BuildingCostChangeRequest
