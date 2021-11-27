package eu.miaplatform.model.request

import com.papsign.ktor.openapigen.annotations.parameters.PathParam

data class HelloWorldPostRequest (
    @PathParam("Description of the param")
    val pathParam: String
)