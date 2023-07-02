package de.sambalmueslie.open.col.app.common

interface SimpleDataObject<T : BusinessObject<*>> : DataObject {
    fun convert(): T

}
