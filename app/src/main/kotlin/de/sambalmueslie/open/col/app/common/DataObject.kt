package de.sambalmueslie.open.col.app.common

interface DataObject<T : BusinessObject<*>> {
	fun convert(): T
}
