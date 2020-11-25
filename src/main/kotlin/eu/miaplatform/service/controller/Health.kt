package eu.miaplatform.service.controller

import eu.miaplatform.service.StatusService
import eu.miaplatform.service.model.HealthBodyResponse
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route

fun Routing.health() {
    val version = StatusService().getVersion()
    val healthBodyResponse = HealthBodyResponse("service-api", version, HealthBodyResponse.Status.OK.value)

    route("/-") {
        get("/healthz") {
            call.respond(HttpStatusCode.OK, healthBodyResponse)
        }
        get("/check-up") {
            // Add service dependencies here
            call.respond(HttpStatusCode.OK, healthBodyResponse)
        }
        get("/ready") {
            call.respond(HttpStatusCode.OK, healthBodyResponse)
        }
    }
}
