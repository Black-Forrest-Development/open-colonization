package de.sambalmueslie.open.col.app.player.api

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

interface PlayerAPI {
    fun create(worldId: Long, request: PlayerChangeRequest): Player
    fun update(id: Long, request: PlayerChangeRequest): Player
    fun delete(id: Long)
    fun get(id: Long): Player?
    fun findByName(name: String): Player?
    fun getAll(pageable: Pageable): Page<Player>
}
