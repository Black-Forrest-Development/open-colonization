package de.sambalmueslie.open.col.app.resource.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface ResourceRepository : PageableRepository<ResourceData, Long> {

    fun findByName(name: String): ResourceData?

}
