package de.sambalmueslie.open.col.app.resource.api

import de.sambalmueslie.open.col.app.common.ReadAPI

interface ResourceAPI : ReadAPI<Long, Resource> {
    fun findByName(name: String): Resource?
}
