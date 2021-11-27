package eu.miaplatform.applications

import eu.miaplatform.core.ktor.CustomApplication
import eu.miaplatform.model.response.health.HealthBodyResponse
import eu.miaplatform.services.StatusService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

class HealthApplication : CustomApplication {
    private val version = StatusService().getVersion()
    private val healthBodyResponse = HealthBodyResponse("service-api", version, HealthBodyResponse.Status.OK.value)

    override fun install(routing: Routing): Unit = routing.run {
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
}
