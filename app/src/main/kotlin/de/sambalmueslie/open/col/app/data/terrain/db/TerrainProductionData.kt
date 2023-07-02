package de.sambalmueslie.open.col.app.data.terrain.db

import de.sambalmueslie.open.col.app.common.DataObject
import de.sambalmueslie.open.col.app.data.production.api.ProductionChain
import de.sambalmueslie.open.col.app.data.terrain.api.Terrain
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainProductionChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "TerrainProduction")
@Table(name = "terrain_production")
data class TerrainProductionData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column() val terrainId: Long,
    @Column() var forested: Double,
    @Column() var woodless: Double,

    @Column() var chainId: Long,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : DataObject {


    companion object {
        fun create(
            data: Terrain,
            request: TerrainProductionChangeRequest,
            chain: ProductionChain,
            timestamp: LocalDateTime
        ): TerrainProductionData {
            return TerrainProductionData(0, data.id, request.forested, request.woodless, chain.id, timestamp)
        }
    }

    fun update(request: TerrainProductionChangeRequest, now: LocalDateTime): TerrainProductionData {
        forested = request.forested
        woodless = request.woodless
        updated = now
        return this
    }
}

