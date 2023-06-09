package de.sambalmueslie.open.col.app.data.player.api

import de.sambalmueslie.open.col.app.common.ReadAPI

interface PlayerAPI : ReadAPI<Long, Player> {
    fun create(worldId: Long, request: PlayerChangeRequest): Player
    fun update(id: Long, request: PlayerChangeRequest): Player
    fun delete(id: Long)
    fun findByName(name: String): Player?
}
