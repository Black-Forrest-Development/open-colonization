package de.sambalmueslie.open.col.app.data.building.api

data class CostItemsChangeRequest(
    val name: String,
    val amount: Int
) :  BuildingCostChangeRequest
