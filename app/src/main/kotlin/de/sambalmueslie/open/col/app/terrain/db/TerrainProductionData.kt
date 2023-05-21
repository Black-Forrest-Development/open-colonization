package de.sambalmueslie.open.col.app.terrain.db

import de.sambalmueslie.open.col.app.resource.api.Resource
import de.sambalmueslie.open.col.app.terrain.api.TerrainProductionChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "TerrainProduction")
@Table(name = "terrain_production")
data class TerrainProductionData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column() val terrainId: Long,

    @Column() var resourceId: Long,
    @Column() var forested: Double,
    @Column() var woodless: Double,


    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {
    companion object {
        fun create(data: TerrainData, resource: Resource, request: TerrainProductionChangeRequest): TerrainProductionData {
            return TerrainProductionData(0, data.id, resource.id, request.forested, request.woodless)
        }
    }
}

