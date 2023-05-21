package de.sambalmueslie.open.col.app.resource.db

import de.sambalmueslie.open.col.app.resource.api.Resource
import de.sambalmueslie.open.col.app.resource.api.ResourceChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Resource")
@Table(name = "resource")
data class ResourceData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column var name: String = "",
    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) {
    fun convert(): Resource {
        return Resource(id, name)
    }

    companion object {
        fun create(request: ResourceChangeRequest): ResourceData {
            return ResourceData(0, request.name, LocalDateTime.now(), null)
        }
    }
}
