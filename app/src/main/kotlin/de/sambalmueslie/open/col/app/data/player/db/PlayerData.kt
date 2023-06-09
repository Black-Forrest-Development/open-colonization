package de.sambalmueslie.open.col.app.data.player.db

import de.sambalmueslie.open.col.app.common.DataObject
import de.sambalmueslie.open.col.app.data.player.api.Player
import de.sambalmueslie.open.col.app.data.player.api.PlayerChangeRequest
import de.sambalmueslie.open.col.app.data.world.api.World
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
) : DataObject<Player> {
    companion object {
        fun create(world: World, request: PlayerChangeRequest, timestamp: LocalDateTime): PlayerData {
            return PlayerData(0, world.id, request.name, timestamp)
        }
    }

    override fun convert() = Player(id, name, worldId)
    fun update(request: PlayerChangeRequest, timestamp: LocalDateTime): PlayerData {
        name = request.name
        updated = timestamp
        return this
    }
}

