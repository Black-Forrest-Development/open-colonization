package de.sambalmueslie.open.col.app.resource.api

import de.sambalmueslie.open.col.app.common.BusinessObjectChangeRequest

data class ResourceChangeRequest(
    val name: String
) : BusinessObjectChangeRequest
