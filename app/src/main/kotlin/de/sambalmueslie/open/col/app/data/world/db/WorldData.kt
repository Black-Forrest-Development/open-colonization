package de.sambalmueslie.open.col.app.data.world.db

import de.sambalmueslie.open.col.app.common.DataObject
import de.sambalmueslie.open.col.app.common.SimpleDataObject
import de.sambalmueslie.open.col.app.data.world.api.World
import de.sambalmueslie.open.col.app.data.world.api.WorldChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "World")
@Table(name = "world")
data class WorldData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column() var name: String,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : SimpleDataObject<World> {
    companion object {
        fun create(request: WorldChangeRequest, timestamp: LocalDateTime): WorldData {
            return WorldData(0, request.name, timestamp)
        }
    }

    override fun convert() = World(id, name)
    fun update(request: WorldChangeRequest, timestamp: LocalDateTime): WorldData {
        name = request.name
        updated = timestamp
        return this
    }
}
