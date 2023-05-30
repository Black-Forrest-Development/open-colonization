package de.sambalmueslie.open.col.app.terrain.api

import de.sambalmueslie.open.col.app.common.ReadAPI

interface TerrainAPI : ReadAPI<Long, Terrain> {
    fun findByName(name: String): Terrain?

}
