package de.sambalmueslie.open.col.app.data.production.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface ProductionChainRepository : PageableRepository<ProductionChainData, Long> {
    fun findByIdIn(ids: Set<Long>): List<ProductionChainData>
}
