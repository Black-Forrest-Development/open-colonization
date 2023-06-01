package de.sambalmueslie.open.col.app.settlement.db

import de.sambalmueslie.open.col.app.player.api.Player
import de.sambalmueslie.open.col.app.settlement.api.Settlement
import de.sambalmueslie.open.col.app.settlement.api.SettlementChangeRequest
import de.sambalmueslie.open.col.app.tile.api.Coordinate
import de.sambalmueslie.open.col.app.world.api.World
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Settlement")
@Table(name = "settlement")
data class SettlementData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column val worldId: Long,
    @Embedded val coordinate: Coordinate,
    @Column var name: String,
    @Column var ownerId: Long,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {
    companion object {
        fun create(world: World, player: Player, request: SettlementChangeRequest): SettlementData {
            return SettlementData(0, world.id, request.coordinate, request.name, player.id, LocalDateTime.now())
        }
    }

    fun convert() = Settlement(id, coordinate, name, worldId, ownerId)
}

