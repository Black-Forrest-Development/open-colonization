package de.sambalmueslie.open.col.app.settlement


import de.sambalmueslie.open.col.app.settlement.api.SettlementAPI
import io.micronaut.http.annotation.Controller
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/api/settlement")
class SettlementController : SettlementAPI {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SettlementController::class.java)
    }


}
