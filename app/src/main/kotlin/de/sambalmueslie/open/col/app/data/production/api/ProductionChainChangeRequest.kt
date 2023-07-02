package de.sambalmueslie.open.col.app.data.production.api

import de.sambalmueslie.open.col.app.common.BusinessObjectChangeRequest

data class ProductionChainChangeRequest(
    val source: List<String>,
    val deliver: List<String>
) : BusinessObjectChangeRequest
