package de.sambalmueslie.open.col.app.data.terrain.db

import de.sambalmueslie.open.col.app.data.terrain.api.Terrain
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainChangeRequest
import de.sambalmueslie.open.col.app.data.world.api.World
import de.sambalmueslie.open.col.app.engine.api.ResourceProduction
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Terrain")
@Table(name = "terrain")
data class TerrainData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column val worldId: Long,
    @Column var name: String = "",

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {

    companion object {
        fun create(world: World, request: TerrainChangeRequest, timestamp: LocalDateTime): TerrainData {
            return TerrainData(0, world.id, request.name, timestamp)
        }
    }


    fun convert(production: List<ResourceProduction>): Terrain {
        return Terrain(id, name, production)
    }

    fun update(request: TerrainChangeRequest, timestamp: LocalDateTime): TerrainData {
        name = request.name
        updated = timestamp
        return this
    }
}
