package de.sambalmueslie.open.col.app.data.settlement.api

import de.sambalmueslie.open.col.app.data.resource.api.Resource

data class ResourceEntry(
    val resource: Resource,
    val amount: Double
)
