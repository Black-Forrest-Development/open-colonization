package de.sambalmueslie.open.col.app.data.building.db

import de.sambalmueslie.open.col.app.data.building.api.EffectItem
import de.sambalmueslie.open.col.app.data.building.api.EffectItemChangeRequest
import de.sambalmueslie.open.col.app.data.item.api.Item
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "EffectItem")
@Table(name = "building_effect_item")
data class EffectItemData(
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
            request: EffectItemChangeRequest,
            timestamp: LocalDateTime
        ): EffectItemData {
            return EffectItemData(0, definition.id, item.id, request.amount, timestamp)
        }
    }

    fun convert(): EffectItem {
        return EffectItem(itemId, amount)
    }


    fun update(request: EffectItemChangeRequest, timestamp: LocalDateTime): EffectItemData {
        amount = request.amount
        updated = timestamp
        return this
    }
}

