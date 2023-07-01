package de.sambalmueslie.open.col.app.data.item.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface ItemRepository : PageableRepository<ItemData, Long> {

    fun findByName(name: String): ItemData?
    fun findByWorldId(id: Long, pageable: Pageable): Page<ItemData>

}
