package de.sambalmueslie.open.col.app.data.settlement


import de.sambalmueslie.open.col.app.data.settlement.api.Settlement
import de.sambalmueslie.open.col.app.data.settlement.api.SettlementAPI
import de.sambalmueslie.open.col.app.data.settlement.api.SettlementChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/api/settlement")
class SettlementController(private val service: SettlementService) : SettlementAPI {

    @Post("/{playerId}")
    override fun create(playerId: Long, @Body request: SettlementChangeRequest): Settlement {
        return service.create(playerId, request)
    }

    override fun update(id: Long, request: SettlementChangeRequest): Settlement {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override fun get(id: Long): Settlement? {
        TODO("Not yet implemented")
    }

    override fun findByName(name: String): Settlement? {
        TODO("Not yet implemented")
    }

    override fun getAll(pageable: Pageable): Page<Settlement> {
        TODO("Not yet implemented")
    }


}
