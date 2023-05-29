package de.sambalmueslie.open.col.app.world.api

import de.sambalmueslie.open.col.app.common.BusinessObjectChangeRequest

data class WorldChangeRequest(
    val name: String
) : BusinessObjectChangeRequest
