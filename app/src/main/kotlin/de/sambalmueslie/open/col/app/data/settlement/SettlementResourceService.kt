package de.sambalmueslie.open.col.app.data.settlement


import de.sambalmueslie.open.col.app.common.BusinessObjectChangeListener
import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.data.resource.ResourceService
import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementResourceEntry
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementResourceId
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementResourceRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class SettlementResourceService(
    private val service: SettlementService,
    private val resourceService: ResourceService,
    private val repository: SettlementResourceRepository
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SettlementResourceService::class.java)
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
        val sequence = PageableSequence() { resourceService.getAll(it) }
        val data = sequence.map { SettlementResourceEntry(SettlementResourceId(settlement.id, it.id), 0.0) }.toList()
        repository.saveAll(data)
    }

    private fun settlementDeleted(settlement: Settlement) {
        repository.deleteBySettlementId(settlement.id)
    }

    fun get(settlement: Settlement) = repository.findBySettlementId(settlement.id)
    fun set(settlement: Settlement, result: List<SettlementResourceEntry>) {
        repository.updateAll(result)
    }


}
