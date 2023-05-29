package de.sambalmueslie.open.col.app.common

interface CrudAPI<T, O : BusinessObject<T>, R : BusinessObjectChangeRequest> : ReadAPI<T, O> {
    fun create(request: R): O
    fun update(id: T, request: R): O
    fun delete(id: T): O?
}
