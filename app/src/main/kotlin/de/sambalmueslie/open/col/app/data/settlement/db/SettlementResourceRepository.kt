package de.sambalmueslie.open.col.app.data.settlement.db

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface SettlementResourceRepository : PageableRepository<SettlementResourceEntry, SettlementResourceId> {


    @Query("SELECT r.* FROM settlement_resource r WHERE r.id_settlement_id = :settlementId")
    fun findBySettlementId(settlementId: Long): List<SettlementResourceEntry>

    @Query("DELETE FROM settlement_resource r WHERE r.id_settlement_id = :settlementId")
    fun deleteBySettlementId(settlementId: Long)

}
