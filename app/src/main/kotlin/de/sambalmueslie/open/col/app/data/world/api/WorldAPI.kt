package de.sambalmueslie.open.col.app.data.world.api

import de.sambalmueslie.open.col.app.common.CrudAPI

interface WorldAPI : CrudAPI<Long, World, WorldChangeRequest> {
    fun findByName(name: String): World?
}
