package de.sambalmueslie.open.col.app.data.building


import de.sambalmueslie.open.col.app.data.building.api.Building
import de.sambalmueslie.open.col.app.data.building.api.BuildingAPI
import de.sambalmueslie.open.col.app.data.building.api.BuildingChangeRequest
import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/building")
@Tag(name = "Building API")
class BuildingController(private val service: BuildingService) : BuildingAPI {


    @Get("/{id}")
    override fun get(id: Long): Building? {
        return service.get(id)
    }
    @Get("/find/by/name")
    override fun findByName(@QueryValue name: String): Building? {
        return service.findByName(name)
    }

    @Get()
    override fun getAll(pageable: Pageable): Page<Building> {
        return service.getAll(pageable)
    }


}
