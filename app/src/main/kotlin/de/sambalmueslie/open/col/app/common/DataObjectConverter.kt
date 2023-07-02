package de.sambalmueslie.open.col.app.common

import io.micronaut.data.model.Page

interface DataObjectConverter<T : BusinessObject<*>, O : DataObject> {
    fun convert(obj: O): T
    fun convert(objs: List<O>): List<T>
    fun convert(page: Page<O>): Page<T>
}
