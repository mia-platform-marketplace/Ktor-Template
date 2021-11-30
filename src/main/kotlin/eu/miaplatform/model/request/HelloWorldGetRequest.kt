package eu.miaplatform.model.request

import com.papsign.ktor.openapigen.annotations.parameters.QueryParam

data class HelloWorldGetRequest (
    @QueryParam("Description of the query param")
    val queryParam: String?
)