package de.sambalmueslie.open.col.app.settlement.db

import de.sambalmueslie.open.col.app.tile.api.Coordinate
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface SettlementRepository : PageableRepository<SettlementData, Long> {
    fun findByWorldId(worldId: Long, pageable: Pageable): Page<SettlementData>

    fun findByCoordinate(coordinate: Coordinate): SettlementData?

}