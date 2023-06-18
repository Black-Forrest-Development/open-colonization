package de.sambalmueslie.open.col.app.engine.service


import de.sambalmueslie.open.col.app.data.resource.api.Resource
import de.sambalmueslie.open.col.app.data.settlement.SettlementResourceService
import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementResourceEntry
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementResourceId
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.math.min

@Singleton
class StorageService(
    private val settlementResourceService: SettlementResourceService
) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(StorageService::class.java)
        private val DEFAULT_LIMIT = 100.0
    }

    fun store(settlement: Settlement, data: Map<Resource, Double>) {
        val current = settlementResourceService.get(settlement)
        val requested = data.map { SettlementResourceEntry(SettlementResourceId(settlement.id, it.key.id), it.value) }
            .associateBy { it.id.resourceId }

        val result = current.map {
            store(settlement, it, requested[it.id.resourceId] ?: SettlementResourceEntry(it.id, 0.0))
        }
        settlementResourceService.set(settlement, result)
    }


    private fun store(
        settlement: Settlement,
        current: SettlementResourceEntry,
        requested: SettlementResourceEntry
    ): SettlementResourceEntry {
        val limit = getLimit(settlement, current)
        val amount = current.amount + requested.amount

        val result = min(limit, amount)
        current.amount = result
        return current
    }

    private fun getLimit(settlement: Settlement, current: SettlementResourceEntry): Double {
        // TODO consider storage capacity
        return DEFAULT_LIMIT
    }


}
