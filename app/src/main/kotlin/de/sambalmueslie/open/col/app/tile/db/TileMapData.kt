package de.sambalmueslie.open.col.app.tile.db

import de.sambalmueslie.open.col.app.tile.api.TileMap
import de.sambalmueslie.open.col.app.tile.api.TileMapChangeRequest
import de.sambalmueslie.open.col.app.world.api.World
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "TileMap")
@Table(name = "tile_map")
data class TileMapData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column val worldId: Long,

    @Column var width: Int,
    @Column var height: Int,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {
    companion object {
        fun create(world: World, request: TileMapChangeRequest): TileMapData {
            return TileMapData(0, world.id, request.width, request.height)
        }
    }

    fun convert() = TileMap(id, width, height, worldId)
}
