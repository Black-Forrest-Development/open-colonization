package de.sambalmueslie.open.col.app.data.terrain.db

import de.sambalmueslie.open.col.app.data.item.api.Item
import de.sambalmueslie.open.col.app.data.terrain.api.TerrainProductionChangeRequest
import de.sambalmueslie.open.col.app.engine.api.ItemProduction
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "TerrainProduction")
@Table(name = "terrain_production")
data class TerrainProductionData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column() val terrainId: Long,

    @Column() var itemId: Long,
    @Column() var forested: Double,
    @Column() var woodless: Double,


    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {
    fun convert() = ItemProduction(itemId, forested, woodless)

    companion object {
        fun create(
            data: TerrainData,
            item: Item,
            request: TerrainProductionChangeRequest,
            timestamp: LocalDateTime
        ): TerrainProductionData {
            return TerrainProductionData(0, data.id, item.id, request.forested, request.woodless, timestamp)
        }
    }
}

