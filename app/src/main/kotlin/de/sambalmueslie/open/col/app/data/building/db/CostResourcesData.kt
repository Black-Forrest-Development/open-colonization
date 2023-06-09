package de.sambalmueslie.open.col.app.data.building.db

import de.sambalmueslie.open.col.app.data.building.api.CostResources
import de.sambalmueslie.open.col.app.data.building.api.CostResourcesChangeRequest
import de.sambalmueslie.open.col.app.data.resource.api.Resource
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "CostResources")
@Table(name = "building_cost_resources")
data class CostResourcesData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column() val buildingId: Long,
    @Column() val resourceId: Long,

    @Column() var amount: Int,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {

    companion object {
        fun create(
            definition: BuildingData,
            resource: Resource,
            request: CostResourcesChangeRequest,
            timestamp: LocalDateTime
        ): CostResourcesData {
            return CostResourcesData(0, definition.id, resource.id, request.amount, timestamp)
        }
    }

    fun convert(): CostResources {
        return CostResources(resourceId, amount)
    }


    fun update(request: CostResourcesChangeRequest, timestamp: LocalDateTime): CostResourcesData {
        amount = request.amount
        updated = timestamp
        return this
    }
}

