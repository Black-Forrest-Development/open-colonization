package de.sambalmueslie.open.col.app.world.api

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse

interface WorldAPI {
    fun create(request: WorldChangeRequest): HttpResponse<String>
    fun get(id: Long): World?
    fun findByName(name: String): World?
    fun getAll(pageable: Pageable): Page<World>

}
