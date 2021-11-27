package eu.miaplatform.core.ktor

import io.ktor.application.*

fun ApplicationCall.headersToProxy(additionalHeaderToProxy: String = ""): Map<String, String> {
    val requestIdHeaderKey = "x-request-id"
    val userIdHeaderKey = System.getenv("USERID_HEADER_KEY") ?: "miauserid"
    val groupsHeaderKey = System.getenv("GROUPS_HEADER_KEY") ?: "miausergroups"
    val clientTypeHeaderKey = System.getenv("CLIENTTYPE_HEADER_KEY") ?: "client-type"
    val backofficeHeaderKey = System.getenv("BACKOFFICE_HEADER_KEY") ?: "isbackoffice"
    val userPropertyHeaderKey = System.getenv("USER_PROPERTIES_HEADER_KEY") ?: "miauserproperties"

    val headers = mutableMapOf<String, String>()

    request.headers[requestIdHeaderKey]?.let { headers[requestIdHeaderKey] = it }
    request.headers[userIdHeaderKey]?.let { headers[userIdHeaderKey] = it }
    request.headers[groupsHeaderKey]?.let { headers[groupsHeaderKey] = it }
    request.headers[clientTypeHeaderKey]?.let { headers[clientTypeHeaderKey] = it }
    request.headers[backofficeHeaderKey]?.let { headers[backofficeHeaderKey] = it }
    request.headers[userPropertyHeaderKey]?.let { headers[userPropertyHeaderKey] = it }

    additionalHeaderToProxy.split(",").forEach { key ->
        val headerValue = request.headers[key]
        headerValue?.let { header ->
            headers[key] =  header
        }
    }

    return headers
}