package de.sambalmueslie.open.col.app.account

import io.micronaut.http.annotation.Controller
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/api/account")
class AccountController {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AccountController::class.java)
    }


}
