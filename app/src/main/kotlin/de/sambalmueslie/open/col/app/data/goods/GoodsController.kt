package de.sambalmueslie.open.col.app.data.goods


import de.sambalmueslie.open.col.app.data.goods.api.Goods
import de.sambalmueslie.open.col.app.data.goods.api.GoodsAPI
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

class GoodsController(private val service: GoodsService) : GoodsAPI {

    @Get("/{id}")
    override fun get(id: Long): Goods? {
        return service.get(id)
    }

    @Get("/find/by/name")
    override fun findByName(@QueryValue name: String): Goods? {
        return service.findByName(name)
    }

    @Get()
    override fun getAll(pageable: Pageable): Page<Goods> {
        return service.getAll(pageable)
    }


}
