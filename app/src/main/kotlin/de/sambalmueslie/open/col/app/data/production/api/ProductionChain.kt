package de.sambalmueslie.open.col.app.data.production.api

import de.sambalmueslie.open.col.app.common.BusinessObject
import de.sambalmueslie.open.col.app.data.item.api.Item

data class ProductionChain(
    override val id: Long,
    val source: List<Item>,
    val deliver: List<Item>
) : BusinessObject<Long>
