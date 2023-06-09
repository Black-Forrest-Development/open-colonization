package de.sambalmueslie.open.col.app.data.building.api

data class EffectGoodsChangeRequest(
    val name: String,
    val amount: Int
) : BuildingEffectChangeRequest
