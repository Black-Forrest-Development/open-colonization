package de.sambalmueslie.open.col.app.data.settlement.db

import jakarta.persistence.Embeddable

@Embeddable
data class SettlementResourceId(
    val settlementId: Long,
    val resourceId: Long
)
