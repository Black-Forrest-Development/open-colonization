package de.sambalmueslie.open.col.app.data.settlement


import de.sambalmueslie.open.col.app.common.BusinessObjectChangeListener
import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.data.item.ItemService
import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementItemEntry
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementItemId
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementItemRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class SettlementItemService(
    private val service: SettlementService,
    private val itemService: ItemService,
    private val repository: SettlementItemRepository
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SettlementItemService::class.java)
    }

    init {
        service.register(object : BusinessObjectChangeListener<Long, Settlement> {
            override fun handleCreated(obj: Settlement) {
                settlementCreated(obj)
            }

            override fun handleDeleted(obj: Settlement) {
                settlementDeleted(obj)
            }

        })
    }

    private fun settlementCreated(settlement: Settlement) {
        val sequence = PageableSequence() { itemService.getAll(it) }
        val data = sequence.map { SettlementItemEntry(SettlementItemId(settlement.id, it.id), 0.0) }.toList()
        repository.saveAll(data)
    }

    private fun settlementDeleted(settlement: Settlement) {
        repository.deleteBySettlementId(settlement.id)
    }

    fun get(settlement: Settlement) = repository.findBySettlementId(settlement.id)
    fun set(settlement: Settlement, result: List<SettlementItemEntry>) {
        repository.updateAll(result)
    }


}
