package de.sambalmueslie.open.col.app.data.terrain.db


import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface TerrainProductionRepository : PageableRepository<TerrainProductionData, Long> {
    fun findByTerrainId(terrainId: Long): List<TerrainProductionData>
    fun findByTerrainIdIn(tIds: Set<Long>): List<TerrainProductionData>
}
