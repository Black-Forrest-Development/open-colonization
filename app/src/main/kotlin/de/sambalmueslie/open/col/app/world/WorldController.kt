package de.sambalmueslie.open.col.app.world


import de.sambalmueslie.open.col.app.world.api.WorldAPI
import io.micronaut.http.annotation.Controller

@Controller("/api/world")
class WorldController(private val service: WorldService) : WorldAPI {

}
