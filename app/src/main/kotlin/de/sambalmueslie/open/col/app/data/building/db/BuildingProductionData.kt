package de.sambalmueslie.open.col.app.data.building.db

import de.sambalmueslie.open.col.app.common.DataObject
import de.sambalmueslie.open.col.app.data.building.api.Building
import de.sambalmueslie.open.col.app.data.building.api.BuildingProductionChangeRequest
import de.sambalmueslie.open.col.app.data.production.api.ProductionChain
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "BuildingProduction")
@Table(name = "building_production")
data class BuildingProductionData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column() val buildingId: Long,
    @Column() var factor: Double,

    @Column() var chainId: Long,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : DataObject {


    companion object {
        fun create(
            data: Building,
            request: BuildingProductionChangeRequest,
            chain: ProductionChain,
            timestamp: LocalDateTime
        ): BuildingProductionData {
            return BuildingProductionData(0, data.id, request.factor, chain.id, timestamp)
        }
    }

    fun update(request: BuildingProductionChangeRequest, now: LocalDateTime): BuildingProductionData {
        factor = request.factor
        updated = now
        return this
    }
}


