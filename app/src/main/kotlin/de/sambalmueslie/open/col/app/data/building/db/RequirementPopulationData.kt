package de.sambalmueslie.open.col.app.data.building.db

import de.sambalmueslie.open.col.app.data.building.api.RequirementPopulation
import de.sambalmueslie.open.col.app.data.building.api.RequirementPopulationChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "RequirementPopulation")
@Table(name = "building_requirement_population")
data class RequirementPopulationData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column var population: Int,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {

    companion object {
        fun create(
            definition: BuildingData,
            request: RequirementPopulationChangeRequest,
            timestamp: LocalDateTime
        ): RequirementPopulationData {
            return RequirementPopulationData(definition.id, request.population, timestamp)
        }
    }

    fun convert(): RequirementPopulation {
        return RequirementPopulation(population)
    }


    fun update(request: RequirementPopulationChangeRequest, timestamp: LocalDateTime): RequirementPopulationData {
        population = request.population
        updated = timestamp
        return this
    }
}
