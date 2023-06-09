package de.sambalmueslie.open.col.app.data.settlement


import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import de.sambalmueslie.open.col.app.data.settlement.api.SettlementAPI
import de.sambalmueslie.open.col.app.data.settlement.api.SettlementChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*

@Controller("/api/settlement")
class SettlementController(private val service: SettlementService) : SettlementAPI {

    @Post("/{playerId}")
    override fun create(playerId: Long, @Body request: SettlementChangeRequest): Settlement {
        return service.create(playerId, request)
    }

    @Put("/{id}")
    override fun update(id: Long, @Body request: SettlementChangeRequest): Settlement {
        return service.update(id, request)
    }

    @Delete("/{id}")
    override fun delete(id: Long): Settlement? {
        return service.delete(id)
    }

    @Get("/{id}")
    override fun get(id: Long): Settlement? {
        return service.get(id)
    }

    @Get("/find/by/name")
    override fun findByName(@QueryValue name: String): Settlement? {
        return service.findByName(name)
    }

    @Get()
    override fun getAll(pageable: Pageable): Page<Settlement> {
        return service.getAll(pageable)
    }


}
