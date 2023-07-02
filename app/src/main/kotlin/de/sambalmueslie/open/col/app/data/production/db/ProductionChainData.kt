package de.sambalmueslie.open.col.app.data.production.db

import de.sambalmueslie.open.col.app.common.DataObject
import de.sambalmueslie.open.col.app.data.item.api.Item
import de.sambalmueslie.open.col.app.data.production.api.ProductionChainChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Suppress("JpaAttributeTypeInspection")
@Entity(name = "ProductionChain")
@Table(name = "production_chain")
data class ProductionChainData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,

    @Column val source: ItemReferences,
    @Column val deliver: ItemReferences,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : DataObject {

    companion object {
        fun create(
            request: ProductionChainChangeRequest,
            source: List<Item>,
            deliver: List<Item>,
            timestamp: LocalDateTime
        ): ProductionChainData {
            return ProductionChainData(
                0,
                ItemReferences((source.map { it.id }.toSet())),
                ItemReferences((deliver.map { it.id }.toSet())),
                timestamp
            )
        }
    }

    fun update(request: ProductionChainChangeRequest, timestamp: LocalDateTime): ProductionChainData {
        TODO("Not yet implemented")
    }

}
