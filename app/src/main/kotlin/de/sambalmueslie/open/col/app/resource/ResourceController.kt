package de.sambalmueslie.open.col.app.resource


import de.sambalmueslie.open.col.app.resource.api.ResourceAPI
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/resource")
@Tag(name = "Resource API")
class ResourceController(
    private val service: ResourceService
) : ResourceAPI {

    @Post("/setup")
    override fun setup(): HttpResponse<String> {
        service.setup()
        return HttpResponse.created("")
    }


}
