package de.sambalmueslie.open.col.app.data.goods.api

import de.sambalmueslie.open.col.app.common.CrudAPI
import de.sambalmueslie.open.col.app.common.ReadAPI
import de.sambalmueslie.open.col.app.data.resource.api.Resource

interface GoodsAPI : ReadAPI<Long, Goods> {
    fun findByName(name: String): Goods?
}
