package de.sambalmueslie.open.col.app.data.building.db

import de.sambalmueslie.open.col.app.data.building.api.*
import de.sambalmueslie.open.col.app.data.world.api.World
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Building")
@Table(name = "building")
data class BuildingData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column val worldId: Long,
    @Column var name: String = "",

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {
    companion object {
        fun create(
            world: World,
            request: BuildingChangeRequest,
            timestamp: LocalDateTime
        ): BuildingData {
            return BuildingData(0, world.id, request.name, timestamp)
        }
    }

    fun convert(
        requirements: List<BuildingRequirement>,
        costs: List<BuildingCost>,
        effect: List<BuildingEffect>
    ): Building {
        return Building(id, name, requirements, costs, effect)
    }

    fun update(request: BuildingChangeRequest, timestamp: LocalDateTime): BuildingData {
        name = request.name
        updated = timestamp
        return this
    }
}
