package de.sambalmueslie.open.col.app.data.settlement.api

import de.sambalmueslie.open.col.app.data.item.api.Item

data class ResourceEntry(
    val item: Item,
    val amount: Double
)
