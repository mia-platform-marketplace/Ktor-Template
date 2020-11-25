package eu.miaplatform.service.controller

import com.papsign.ktor.openapigen.openAPIGen
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.documentation(application: Application) {
    route("/documentation") {
        get {
            call.respondRedirect("/swagger-ui/index.html?url=/documentation/openapi.json", true)
        }
        get("/openapi.json") {
            call.respond(application.openAPIGen.api.serialize())
        }
    }
}
