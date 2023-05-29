package de.sambalmueslie.open.col.app.resource


import de.sambalmueslie.open.col.app.resource.api.Resource
import de.sambalmueslie.open.col.app.resource.api.ResourceAPI
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/resource")
@Tag(name = "Resource API")
class ResourceController(
    private val service: ResourceService
) : ResourceAPI {

    @Get("/{id}")
    override fun get(id: Long): Resource? {
        return service.get(id)
    }

    @Get("/find/by/name")
    override fun findByName(@QueryValue name: String): Resource? {
       return service.findByName(name)
    }

    @Get()
    override fun getAll(pageable: Pageable): Page<Resource> {
        return service.getAll(pageable)
    }


}
