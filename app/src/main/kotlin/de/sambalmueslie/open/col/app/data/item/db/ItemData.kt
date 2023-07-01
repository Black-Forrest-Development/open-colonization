package de.sambalmueslie.open.col.app.data.item.db

import de.sambalmueslie.open.col.app.common.DataObject
import de.sambalmueslie.open.col.app.data.item.api.Item
import de.sambalmueslie.open.col.app.data.item.api.ItemChangeRequest
import de.sambalmueslie.open.col.app.data.world.api.World
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Item")
@Table(name = "item")
data class ItemData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column val worldId: Long,

    @Column var name: String = "",
    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : DataObject<Item> {

    companion object {
        fun create(world: World, request: ItemChangeRequest, timestamp: LocalDateTime): ItemData {
            return ItemData(0, world.id, request.name, timestamp, null)
        }
    }

    override fun convert(): Item {
        return Item(id, name)
    }

    fun update(request: ItemChangeRequest, timestamp: LocalDateTime): ItemData {
        name = request.name
        updated = timestamp
        return this
    }

}
