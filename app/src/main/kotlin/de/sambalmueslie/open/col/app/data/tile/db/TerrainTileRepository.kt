package de.sambalmueslie.open.col.app.data.tile.db

import de.sambalmueslie.open.col.app.data.tile.api.Coordinate
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface TerrainTileRepository : PageableRepository<TerrainTileData, Long> {
    fun findByLayerId(layerId: Long, pageable: Pageable): Page<TerrainTileData>
    fun findByLayerIdAndCoordinate(layerId: Long, coordinate: Coordinate): TerrainTileData?
    fun deleteByLayerId(layerId: Long)
}
