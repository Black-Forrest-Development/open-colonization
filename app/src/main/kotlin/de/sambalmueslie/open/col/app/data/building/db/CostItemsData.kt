package de.sambalmueslie.open.col.app.data.building.db

import de.sambalmueslie.open.col.app.data.building.api.CostItems
import de.sambalmueslie.open.col.app.data.building.api.CostItemsChangeRequest
import de.sambalmueslie.open.col.app.data.item.api.Item
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "CostItems")
@Table(name = "building_cost_items")
data class CostItemsData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column() val buildingId: Long,
    @Column() val itemId: Long,

    @Column() var amount: Int,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {

    companion object {
        fun create(
            definition: BuildingData,
            item: Item,
            request: CostItemsChangeRequest,
            timestamp: LocalDateTime
        ): CostItemsData {
            return CostItemsData(0, definition.id, item.id, request.amount, timestamp)
        }
    }

    fun convert(): CostItems {
        return CostItems(itemId, amount)
    }


    fun update(request: CostItemsChangeRequest, timestamp: LocalDateTime): CostItemsData {
        amount = request.amount
        updated = timestamp
        return this
    }
}

