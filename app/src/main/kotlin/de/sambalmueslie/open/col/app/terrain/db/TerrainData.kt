package de.sambalmueslie.open.col.app.terrain.db

import de.sambalmueslie.open.col.app.engine.api.ResourceProduction
import de.sambalmueslie.open.col.app.terrain.api.Terrain
import de.sambalmueslie.open.col.app.terrain.api.TerrainChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Terrain")
@Table(name = "terrain")
data class TerrainData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column var name: String = "",


    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {
    fun convert(production: List<ResourceProduction>): Terrain {
        return Terrain(id, name, production)
    }

    companion object {
        fun create(request: TerrainChangeRequest): TerrainData {
            return TerrainData(0, request.name, LocalDateTime.now(), null)
        }
    }
}
