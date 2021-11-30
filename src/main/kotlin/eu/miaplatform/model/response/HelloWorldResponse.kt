package eu.miaplatform.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class HelloWorldResponse (
    @JsonProperty("pathParamSent")
    @get:JsonProperty("pathParamSent")
    val pathParam: String?,

    @JsonProperty("queryParamSent")
    @get:JsonProperty("queryParamSent")
    val queryParam: String?,

    @JsonProperty("helloWorld")
    @get:JsonProperty("helloWorld")
    val helloWorld: String?
)