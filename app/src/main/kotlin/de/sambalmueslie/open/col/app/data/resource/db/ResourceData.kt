package de.sambalmueslie.open.col.app.data.resource.db

import de.sambalmueslie.open.col.app.common.DataObject
import de.sambalmueslie.open.col.app.data.resource.api.Resource
import de.sambalmueslie.open.col.app.data.resource.api.ResourceChangeRequest
import de.sambalmueslie.open.col.app.data.world.api.World
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Resource")
@Table(name = "resource")
data class ResourceData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column val worldId: Long,

    @Column var name: String = "",
    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : DataObject<Resource> {

    companion object {
        fun create(world: World, request: ResourceChangeRequest, timestamp: LocalDateTime): ResourceData {
            return ResourceData(0, world.id, request.name, timestamp, null)
        }
    }

    override fun convert(): Resource {
        return Resource(id, name)
    }

    fun update(request: ResourceChangeRequest, timestamp: LocalDateTime): ResourceData {
        name = request.name
        updated = timestamp
        return this
    }

}