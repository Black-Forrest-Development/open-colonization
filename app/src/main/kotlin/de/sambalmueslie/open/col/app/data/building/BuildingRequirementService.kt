package de.sambalmueslie.open.col.app.data.building


import de.sambalmueslie.open.col.app.common.TimeProvider
import de.sambalmueslie.open.col.app.common.findByIdOrNull
import de.sambalmueslie.open.col.app.data.building.api.BuildingRequirement
import de.sambalmueslie.open.col.app.data.building.api.BuildingRequirementChangeRequest
import de.sambalmueslie.open.col.app.data.building.api.RequirementPopulation
import de.sambalmueslie.open.col.app.data.building.api.RequirementPopulationChangeRequest
import de.sambalmueslie.open.col.app.data.building.db.BuildingData
import de.sambalmueslie.open.col.app.data.building.db.RequirementPopulationData
import de.sambalmueslie.open.col.app.data.building.db.RequirementPopulationRepository
import de.sambalmueslie.open.col.app.error.InvalidRequestException
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class BuildingRequirementService(
    private val populationRepository: RequirementPopulationRepository,
    private val timeProvider: TimeProvider,
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BuildingRequirementService::class.java)
    }

    internal fun get(data: BuildingData): List<BuildingRequirement> {
        val population = populationRepository.findByIdOrNull(data.id) ?: return emptyList()
        return listOf(population.convert())
    }

    fun get(buildingIds: Set<Long>): Map<Long, List<BuildingRequirement>> {
        return populationRepository.findByIdIn(buildingIds)
            .groupBy { it.id }
            .mapValues { it.value.map { v -> v.convert() } }
    }

    internal fun create(
        data: BuildingData,
        requirements: List<BuildingRequirementChangeRequest>
    ): List<BuildingRequirement> {
        return requirements.map { create(data, it) }
    }

    private fun create(data: BuildingData, request: BuildingRequirementChangeRequest): BuildingRequirement {
        return when (request) {
            is RequirementPopulationChangeRequest -> create(data, request)
            else -> throw InvalidRequestException("Unknown type of building requirement ${request.javaClass.simpleName}")
        }
    }

    private fun create(
        data: BuildingData,
        request: RequirementPopulationChangeRequest
    ): RequirementPopulation {
        return populationRepository.save(RequirementPopulationData.create(data, request, timeProvider.now())).convert()
    }

}
