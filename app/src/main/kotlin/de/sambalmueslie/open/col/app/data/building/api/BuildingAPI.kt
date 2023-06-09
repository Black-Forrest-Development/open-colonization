package de.sambalmueslie.open.col.app.data.building.api

import de.sambalmueslie.open.col.app.common.ReadAPI

interface BuildingAPI : ReadAPI<Long, Building> {
    fun findByName(name: String): Building?
}
