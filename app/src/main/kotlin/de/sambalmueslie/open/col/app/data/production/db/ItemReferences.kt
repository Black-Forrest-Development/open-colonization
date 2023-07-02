package de.sambalmueslie.open.col.app.data.production.db

import io.micronaut.data.annotation.TypeDef
import io.micronaut.data.model.DataType

@TypeDef(type = DataType.STRING, converter = ItemReferencesConverter::class)
data class ItemReferences(
    val itemIds: Set<Long> = emptySet()
)
