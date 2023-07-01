package de.sambalmueslie.open.col.app.data.building.api

import de.sambalmueslie.open.col.app.common.BusinessObject

/**
 * A Building contains of
 * - Requirements to start the construction like population
 * - Costs to build the building (workforce, item ...)
 * - Effect of the finished building like production of items or defense bonus
 */
data class Building(
    override val id: Long,
    val name: String,
    val requirements: List<BuildingRequirement>,
    val costs: List<BuildingCost>,
    val effect: List<BuildingEffect>
) : BusinessObject<Long>
