package de.sambalmueslie.open.col.app.resource.api

import io.micronaut.http.HttpResponse

interface ResourceAPI {
    fun setup(): HttpResponse<String>
}
