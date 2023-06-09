package de.sambalmueslie.open.col.app.data.goods.db

import de.sambalmueslie.open.col.app.common.DataObject
import de.sambalmueslie.open.col.app.data.goods.api.Goods
import de.sambalmueslie.open.col.app.data.goods.api.GoodsChangeRequest
import de.sambalmueslie.open.col.app.data.resource.api.ResourceChangeRequest
import de.sambalmueslie.open.col.app.data.resource.db.ResourceData
import de.sambalmueslie.open.col.app.data.world.api.World
import jakarta.persistence.*
import java.time.LocalDateTime
@Entity(name = "Goods")
@Table(name = "goods")
data class GoodsData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column val worldId: Long,
    @Column var name: String = "",
    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : DataObject<Goods> {
    companion object {
        fun create(world: World, request: GoodsChangeRequest, timestamp: LocalDateTime): GoodsData {
            return GoodsData(0, world.id, request.name, timestamp, null)
        }
    }

    override fun convert(): Goods {
        return Goods(id, name)
    }

    fun update(request: GoodsChangeRequest, timestamp: LocalDateTime): GoodsData {
        name = request.name
        updated = timestamp
        return this
    }
}
