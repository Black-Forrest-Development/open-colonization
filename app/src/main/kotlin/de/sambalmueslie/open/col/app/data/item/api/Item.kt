package de.sambalmueslie.open.col.app.data.item.api

import de.sambalmueslie.open.col.app.common.BusinessObject


data class Item(
    override val id: Long,
    val name: String
) : BusinessObject<Long>
