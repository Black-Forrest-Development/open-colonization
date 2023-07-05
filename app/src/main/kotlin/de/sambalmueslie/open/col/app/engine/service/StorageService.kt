package de.sambalmueslie.open.col.app.engine.service


import de.sambalmueslie.open.col.app.data.building.BuildingService
import de.sambalmueslie.open.col.app.data.item.api.Item
import de.sambalmueslie.open.col.app.data.settlement.SettlementBuildingService
import de.sambalmueslie.open.col.app.data.settlement.SettlementItemService
import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementItemEntry
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementItemId
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.math.min

@Singleton
class StorageService(
    private val settlementItemService: SettlementItemService
) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(StorageService::class.java)
        private val DEFAULT_LIMIT = 100.0
    }

    fun store(settlement: Settlement, data: Map<Item, Double>) {
        val current = settlementItemService.get(settlement)
        val requested = data.map { SettlementItemEntry(SettlementItemId(settlement.id, it.key.id), it.value) }
            .associateBy { it.id.itemId }

        val result = current.map {
            store(settlement, it, requested[it.id.itemId] ?: SettlementItemEntry(it.id, 0.0))
        }
        settlementItemService.set(settlement, result)
    }


    private fun store(
        settlement: Settlement,
        current: SettlementItemEntry,
        requested: SettlementItemEntry
    ): SettlementItemEntry {
        val limit = getLimit(settlement, current)
        val amount = current.amount + requested.amount

        val result = min(limit, amount)
        current.amount = result
        return current
    }

    private fun getLimit(settlement: Settlement, current: SettlementItemEntry): Double {
        // TODO consider storage capacity
        return DEFAULT_LIMIT
    }


}
