package de.sambalmueslie.open.col.app.data.settlement.api

import de.sambalmueslie.open.col.app.common.ReadAPI

interface SettlementAPI : ReadAPI<Long, Settlement> {
    fun create(playerId: Long, request: SettlementChangeRequest): Settlement
    fun update(id: Long, request: SettlementChangeRequest): Settlement
    fun delete(id: Long): Settlement?
    fun findByName(name: String): Settlement?
}
