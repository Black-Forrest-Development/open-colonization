package de.sambalmueslie.open.col.app.world.db

import de.sambalmueslie.open.col.app.world.api.World
import de.sambalmueslie.open.col.app.world.api.WorldChangeRequest
import jakarta.persistence.*

@Entity(name = "World")
@Table(name = "world")
data class WorldData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column() var name: String,
) {
    companion object {
        fun create(request: WorldChangeRequest): WorldData {
            return WorldData(0, request.name)
        }
    }

    fun convert() = World(id, name)
}
