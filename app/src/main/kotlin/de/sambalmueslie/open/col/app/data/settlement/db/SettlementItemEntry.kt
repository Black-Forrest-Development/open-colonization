package de.sambalmueslie.open.col.app.data.settlement.db

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Suppress("JpaAttributeTypeInspection")
@Entity(name = "SettlementItem")
@Table(name = "settlement_item")
data class SettlementItemEntry(
    @EmbeddedId val id: SettlementItemId,
    @Column var amount: Double
)
