package de.sambalmueslie.open.col.app.data.item.api

import de.sambalmueslie.open.col.app.common.ReadAPI

interface ItemAPI : ReadAPI<Long, Item> {
    fun findByName(name: String): Item?
}
