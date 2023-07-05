package de.sambalmueslie.open.col.app.data.settlement.db

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface SettlementBuildingRepository : PageableRepository<SettlementBuildingEntry, SettlementBuildingId> {


    @Query("SELECT b.* FROM settlement_building b WHERE b.id_settlement_id = :settlementId")
    fun findBySettlementId(settlementId: Long): List<SettlementBuildingEntry>

    @Query("DELETE FROM settlement_building b WHERE b.id_settlement_id = :settlementId")
    fun deleteBySettlementId(settlementId: Long)

    @Query("SELECT b.* FROM settlement_building b WHERE b.id_settlement_id = :settlementId and b.level >= :level")
    fun findByLevel(settlementId: Long, level: Int): List<SettlementBuildingEntry>
}
