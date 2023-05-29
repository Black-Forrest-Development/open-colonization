package de.sambalmueslie.open.col.app.tile.db

import de.sambalmueslie.open.col.app.tile.api.TileLayer
import de.sambalmueslie.open.col.app.tile.api.TileLayerChangeRequest
import de.sambalmueslie.open.col.app.tile.api.TileLayerType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "TileLayer")
@Table(name = "tile_layer")
data class TileLayerData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column val mapId: Long,
    @Column @Enumerated(EnumType.STRING) val type: TileLayerType,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {
    companion object {
        fun create(map: TileMapData, request: TileLayerChangeRequest): TileLayerData {
            return TileLayerData(0, map.id, request.type)
        }
    }

    fun convert() = TileLayer(id, type)
}
