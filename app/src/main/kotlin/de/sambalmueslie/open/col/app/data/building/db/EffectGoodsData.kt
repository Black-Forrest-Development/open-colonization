package de.sambalmueslie.open.col.app.data.building.db

import de.sambalmueslie.open.col.app.data.building.api.EffectGoods
import de.sambalmueslie.open.col.app.data.building.api.EffectGoodsChangeRequest
import de.sambalmueslie.open.col.app.data.goods.api.Goods
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "EffectGoods")
@Table(name = "building_effect_goods")
data class EffectGoodsData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column() val buildingId: Long,
    @Column() val goodsId: Long,

    @Column() var amount: Int,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {

    companion object {
        fun create(
            definition: BuildingData,
            goods: Goods,
            request: EffectGoodsChangeRequest,
            timestamp: LocalDateTime
        ): EffectGoodsData {
            return EffectGoodsData(0, definition.id, goods.id, request.amount, timestamp)
        }
    }

    fun convert(): EffectGoods {
        return EffectGoods(goodsId, amount)
    }


    fun update(request: EffectGoodsChangeRequest, timestamp: LocalDateTime): EffectGoodsData {
        amount = request.amount
        updated = timestamp
        return this
    }
}

