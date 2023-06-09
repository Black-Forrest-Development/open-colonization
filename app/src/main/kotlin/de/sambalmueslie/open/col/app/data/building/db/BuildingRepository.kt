package de.sambalmueslie.open.col.app.data.building.db


import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface BuildingRepository : PageableRepository<BuildingData, Long> {
    fun findByName(name: String): BuildingData?
    fun findByWorldId(id: Long, pageable: Pageable): Page<BuildingData>
}
