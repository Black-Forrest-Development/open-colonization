package de.sambalmueslie.open.col.app.data.settlement.api

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

interface SettlementAPI {
    fun create(playerId: Long, request: SettlementChangeRequest): Settlement
    fun update(id: Long, request: SettlementChangeRequest): Settlement
    fun delete(id: Long)
    fun get(id: Long): Settlement?
    fun findByName(name: String): Settlement?
    fun getAll(pageable: Pageable): Page<Settlement>
}
