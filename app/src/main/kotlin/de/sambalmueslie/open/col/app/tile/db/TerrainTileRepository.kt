package de.sambalmueslie.open.col.app.tile.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface TerrainTileRepository : PageableRepository<TerrainTileData, Long> {
    fun findByLayerId(layerId: Long): List<TerrainTileData>
    fun findByLayerIdIn(lIds: Set<Long>): List<TerrainTileData>
}
