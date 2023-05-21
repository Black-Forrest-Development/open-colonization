package de.sambalmueslie.open.col.app.player


import io.micronaut.http.annotation.Controller

@Controller("/api/player")
class PlayerController(private val service: PlayerService) {


}
