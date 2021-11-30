package eu.miaplatform.core.ktor

import com.papsign.ktor.openapigen.route.apiRouting
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import io.ktor.application.*
import io.ktor.routing.*

interface CustomApplication {
    fun install(routing: Routing)
}

interface CustomApiApplication {
    fun install(apiRouting: NormalOpenAPIRoute)
}

fun Application.install(app: CustomApplication) {
    routing { app.install(this) }
}

fun Application.install(app: CustomApiApplication) {
    apiRouting { app.install(this) }
}