package de.sambalmueslie.open.col.app.data.settlement.db

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Suppress("JpaAttributeTypeInspection")
@Entity(name = "SettlementResource")
@Table(name = "settlement_resource")
data class SettlementResourceEntry(
    @EmbeddedId val id: SettlementResourceId,
    @Column var amount: Double
)
