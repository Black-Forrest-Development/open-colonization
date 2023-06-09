package de.sambalmueslie.open.col.app.data.goods.api

import de.sambalmueslie.open.col.app.common.BusinessObject

data class Goods(
    override val id: Long,
    val name: String
) : BusinessObject<Long>
