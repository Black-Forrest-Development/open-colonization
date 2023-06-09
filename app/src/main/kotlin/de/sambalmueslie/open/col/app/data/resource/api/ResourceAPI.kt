package de.sambalmueslie.open.col.app.data.resource.api

import de.sambalmueslie.open.col.app.common.ReadAPI

interface ResourceAPI : ReadAPI<Long, Resource> {
    fun findByName(name: String): Resource?
}
