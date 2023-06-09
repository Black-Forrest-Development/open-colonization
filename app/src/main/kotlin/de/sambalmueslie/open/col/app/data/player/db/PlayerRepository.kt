package de.sambalmueslie.open.col.app.data.player.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface PlayerRepository : PageableRepository<PlayerData, Long> {
    fun findByName(name: String): PlayerData?

}
