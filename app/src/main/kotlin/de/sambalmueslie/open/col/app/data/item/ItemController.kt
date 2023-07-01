package de.sambalmueslie.open.col.app.data.item


import de.sambalmueslie.open.col.app.data.item.api.Item
import de.sambalmueslie.open.col.app.data.item.api.ItemAPI
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/item")
@Tag(name = "Item API")
class ItemController(
    private val service: ItemService
) : ItemAPI {

    @Get("/{id}")
    override fun get(id: Long): Item? {
        return service.get(id)
    }

    @Get("/find/by/name")
    override fun findByName(@QueryValue name: String): Item? {
        return service.findByName(name)
    }

    @Get()
    override fun getAll(pageable: Pageable): Page<Item> {
        return service.getAll(pageable)
    }


}
