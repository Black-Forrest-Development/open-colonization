package de.sambalmueslie.open.col.app.player.db

import de.sambalmueslie.open.col.app.player.api.Player
import de.sambalmueslie.open.col.app.player.api.PlayerChangeRequest
import de.sambalmueslie.open.col.app.world.api.World
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Player")
@Table(name = "player")
data class PlayerData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column() val worldId: Long,
    @Column() var name: String,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {
    companion object {
        fun create(world: World, request: PlayerChangeRequest): PlayerData {
            return PlayerData(0, world.id, request.name)
        }
    }

    fun convert() = Player(id, name)
    fun update(request: PlayerChangeRequest): PlayerData {
        name = request.name
        return this
    }
}

