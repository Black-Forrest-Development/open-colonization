package de.sambalmueslie.open.col.app.data.settlement.db

import jakarta.persistence.Embeddable

@Embeddable
data class SettlementItemId(
    val settlementId: Long,
    val itemId: Long
)
