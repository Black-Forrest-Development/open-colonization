package de.sambalmueslie.open.col.app.data.terrain.db

import de.sambalmueslie.open.col.app.engine.api.ResourceProduction
import de.sambalmueslie.open.col.app.data.resource.api.Resource
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainProductionChangeRequest
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
    fun convert() = ResourceProduction(resourceId, forested, woodless)

    companion object {
        fun create(data: TerrainData, resource: Resource, request: TerrainProductionChangeRequest): TerrainProductionData {
            return TerrainProductionData(0, data.id, resource.id, request.forested, request.woodless)
        }
    }
}

