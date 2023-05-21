package de.sambalmueslie.open.col.app.terrain


import de.sambalmueslie.open.col.app.terrain.api.TerrainAPI
import io.micronaut.http.annotation.Controller

@Controller("/api/nation")
class TerrainController(private val service: TerrainService) : TerrainAPI {


}
