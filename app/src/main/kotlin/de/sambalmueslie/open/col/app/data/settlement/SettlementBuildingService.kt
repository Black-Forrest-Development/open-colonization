package de.sambalmueslie.open.col.app.data.settlement


import de.sambalmueslie.open.col.app.common.BusinessObjectChangeListener
import de.sambalmueslie.open.col.app.common.PageableSequence
import de.sambalmueslie.open.col.app.data.building.BuildingService
import de.sambalmueslie.open.col.app.data.building.api.Building
import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementBuildingEntry
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementBuildingId
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementBuildingRepository
import de.sambalmueslie.open.col.app.data.settlement.db.SettlementItemEntry
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class SettlementBuildingService(
    private val service: SettlementService,
    private val buildingService: BuildingService,
    private val repository: SettlementBuildingRepository
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SettlementBuildingService::class.java)
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
        val sequence = PageableSequence() { buildingService.getAll(it) }
        val data =
            sequence.map { SettlementBuildingEntry(SettlementBuildingId(settlement.id, it.id), it.minLevel) }.toList()
        repository.saveAll(data)
    }

    private fun settlementDeleted(settlement: Settlement) {
        repository.deleteBySettlementId(settlement.id)
    }

    fun getBuildings(settlement: Settlement): List<SettlementBuildingEntry> {
        return repository.findBySettlementId(settlement.id)
    }
}
