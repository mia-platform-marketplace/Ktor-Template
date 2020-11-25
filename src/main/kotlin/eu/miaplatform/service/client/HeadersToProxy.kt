package eu.miaplatform.service.client

import io.ktor.application.*

class HeadersToProxy(
    private val additionalHeaderToProxy: String = ""
) {
    private val requestIdHeaderKey = "x-request-id"
    private val userIdHeaderKey = System.getenv("USERID_HEADER_KEY") ?: "miauserid"
    private val groupsHeaderKey = System.getenv("GROUPS_HEADER_KEY") ?: "miausergroups"
    private val clientTypeHeaderKey = System.getenv("CLIENTTYPE_HEADER_KEY") ?: "client-type"
    private val backofficeHeaderKey = System.getenv("BACKOFFICE_HEADER_KEY") ?: "isbackoffice"
    private val userPropertyHeaderKey = System.getenv("USER_PROPERTIES_HEADER_KEY") ?: "miauserproperties"

    fun proxy(applicationCall: ApplicationCall): Map<String, String> {

        val requestIdHeader = applicationCall.request.headers[requestIdHeaderKey]
        val miaUserIdHeader = applicationCall.request.headers[userIdHeaderKey]
        val groupsHeader = applicationCall.request.headers[groupsHeaderKey]
        val clientTypeHeader = applicationCall.request.headers[clientTypeHeaderKey]
        val backofficeHeader = applicationCall.request.headers[backofficeHeaderKey]
        val userPropertiesHeaderKey = applicationCall.request.headers[userPropertyHeaderKey]

        val headers = mutableMapOf<String, String>()

        requestIdHeader?.let { headerValue ->
            headers[requestIdHeaderKey] = headerValue
        }

        miaUserIdHeader?.let { headerValue ->
            headers[userIdHeaderKey] = headerValue
        }

        groupsHeader?.let { headerValue ->
            headers[groupsHeaderKey] = headerValue
        }

        clientTypeHeader?.let { headerValue ->
            headers[clientTypeHeaderKey] = headerValue
        }

        backofficeHeader?.let { headerValue ->
            headers[backofficeHeaderKey] =  headerValue
        }

        userPropertiesHeaderKey?.let { headerValue ->
            headers[userPropertyHeaderKey] =  headerValue
        }

        additionalHeaderToProxy.split(",").forEach { key ->
            val headerValue = applicationCall.request.headers[key]
            headerValue?.let { header ->
                headers[key] =  header
            }
        }

        return headers
    }
}