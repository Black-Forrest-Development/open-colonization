package de.sambalmueslie.open.col.app.resource.api

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse

interface ResourceAPI {
    fun setup(): HttpResponse<String>

    fun get(id: Long): Resource?
    fun findByName(name: String): Resource?
    fun getAll(pageable: Pageable): Page<Resource>

}
