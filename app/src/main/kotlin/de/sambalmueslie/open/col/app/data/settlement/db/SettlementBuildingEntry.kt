package de.sambalmueslie.open.col.app.data.settlement.db

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Suppress("JpaAttributeTypeInspection")
@Entity(name = "SettlementBuilding")
@Table(name = "settlement_building")
data class SettlementBuildingEntry(
    @EmbeddedId val id: SettlementBuildingId,
    @Column var level: Int
)
