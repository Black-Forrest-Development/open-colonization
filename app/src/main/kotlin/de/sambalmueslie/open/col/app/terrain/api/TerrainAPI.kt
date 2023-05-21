package de.sambalmueslie.open.col.app.terrain.api

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse

interface TerrainAPI {
    fun setup(): HttpResponse<String>

    fun get(id: Long): Terrain?
    fun findByName(name: String): Terrain?
    fun getAll(pageable: Pageable): Page<Terrain>
}
