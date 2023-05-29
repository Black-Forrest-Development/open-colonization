package de.sambalmueslie.open.col.app.tile.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface TileLayerRepository : PageableRepository<TileLayerData, Long> {

    fun findByMapId(mapId: Long): List<TileLayerData>
    fun findByMapIdIn(mIds: Set<Long>): List<TileLayerData>
}
