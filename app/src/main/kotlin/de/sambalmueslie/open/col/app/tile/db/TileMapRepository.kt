package de.sambalmueslie.open.col.app.tile.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface TileMapRepository : PageableRepository<TileMapData, Long> {

    fun findByWorldId(worldId: Long): TileMapData?
    fun findByWorldIdIn(wIds: Set<Long>): List<TileMapData>
}
