package de.sambalmueslie.open.col.app.common

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

interface ReadAPI<T, O : BusinessObject<T>> {
    fun get(id: T): O?
    fun getAll(pageable: Pageable): Page<O>
}
