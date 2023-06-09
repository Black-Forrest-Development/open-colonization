package de.sambalmueslie.open.col.app.data.resource.api

import de.sambalmueslie.open.col.app.common.BusinessObject


data class Resource(
    override val id: Long,
    val name: String
) : BusinessObject<Long>
