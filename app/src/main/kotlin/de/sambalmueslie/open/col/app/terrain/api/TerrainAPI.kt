package de.sambalmueslie.open.col.app.terrain.api

import io.micronaut.http.HttpResponse

interface TerrainAPI {
    fun setup(): HttpResponse<String>
}
