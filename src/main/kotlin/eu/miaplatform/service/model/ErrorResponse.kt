package eu.miaplatform.service.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse (
    @JsonProperty("code")
    val code: Int,

    @JsonProperty("error")
    val error: String
)