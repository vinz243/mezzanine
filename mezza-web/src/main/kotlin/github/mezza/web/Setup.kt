package github.mezza.web

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

fun Application.setup() {
    install(StatusPages) {
        exception<MissingParamException> {
            call.respond(HttpStatusCode.BadRequest)
        }
        exception<EntityNotFoundException> {
            call.respond(HttpStatusCode.NotFound)
        }
    }
    install(ContentNegotiation) {
        gson {
            // Configure Gson here
        }
    }

}