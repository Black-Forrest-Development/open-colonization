package de.sambalmueslie.open.col.app.data.building.api

data class EffectItemChangeRequest(
    val name: String,
    val amount: Int
) : BuildingEffectChangeRequest
