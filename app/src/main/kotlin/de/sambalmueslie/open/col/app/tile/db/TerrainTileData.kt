@file:Suppress("JpaAttributeTypeInspection")

package de.sambalmueslie.open.col.app.tile.db

import de.sambalmueslie.open.col.app.terrain.api.Terrain
import de.sambalmueslie.open.col.app.tile.api.Coordinate
import de.sambalmueslie.open.col.app.tile.api.TerrainTile
import jakarta.persistence.*

@Entity(name = "TerrainTile")
@Table(name = "tile_terrain")
data class TerrainTileData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Embedded val coordinate: Coordinate,
    @Column val layerId: Long,
    @Column var terrainId: Long,
) {
    companion object {
        fun create(layer: TileLayerData, terrain: Terrain, coordinate: Coordinate): TerrainTileData {
            return TerrainTileData(0, coordinate, layer.id, terrain.id)
        }
    }

    fun convert() = TerrainTile(coordinate, terrainId)
}

