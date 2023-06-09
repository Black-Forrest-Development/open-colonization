package de.sambalmueslie.open.col.app.data.goods.api

import de.sambalmueslie.open.col.app.common.ReadAPI

interface GoodsAPI : ReadAPI<Long, Goods> {
    fun findByName(name: String): Goods?
}
