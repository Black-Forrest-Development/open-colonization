package de.sambalmueslie.open.col.app.tile.api

import jakarta.persistence.Embeddable

@Embeddable
data class Coordinate(
    val x: Int,
    val y: Int
)
