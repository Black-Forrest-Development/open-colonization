package de.sambalmueslie.open.col.app.data.settlement.db

import de.sambalmueslie.open.col.app.common.DataObject
import de.sambalmueslie.open.col.app.data.player.api.Player
import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import de.sambalmueslie.open.col.app.data.settlement.api.SettlementChangeRequest
import de.sambalmueslie.open.col.app.data.tile.api.Coordinate
import de.sambalmueslie.open.col.app.data.world.api.World
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
) : DataObject<Settlement> {
    companion object {
        fun create(world: World, player: Player, request: SettlementChangeRequest, timestamp: LocalDateTime): SettlementData {
            return SettlementData(0, world.id, request.coordinate, request.name, player.id, timestamp)
        }
    }

    override fun convert() = Settlement(id, coordinate, name, worldId, ownerId)
    fun update(request: SettlementChangeRequest, timestamp: LocalDateTime): SettlementData {
        name = request.name
        updated = timestamp
        return this
    }
}

