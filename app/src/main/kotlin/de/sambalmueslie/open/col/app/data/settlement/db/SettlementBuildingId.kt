package de.sambalmueslie.open.col.app.data.settlement.db

import jakarta.persistence.Embeddable

@Embeddable
data class SettlementBuildingId(
    val settlementId: Long,
    val buildingId: Long
)
