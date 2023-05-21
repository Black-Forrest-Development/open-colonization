package de.sambalmueslie.open.col.app.terrain


import de.sambalmueslie.open.col.app.terrain.api.TerrainAPI
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/terrain")
@Tag(name = "Terrain API")
class TerrainController(
    private val service: TerrainService
) : TerrainAPI {
    @Post("/setup")
    override fun setup(): HttpResponse<String> {
        service.setup()
        return HttpResponse.created("")
    }

}
