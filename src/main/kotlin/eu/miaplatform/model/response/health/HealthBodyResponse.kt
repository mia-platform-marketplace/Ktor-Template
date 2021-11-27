package eu.miaplatform.model.response.health

import com.fasterxml.jackson.annotation.JsonProperty

data class HealthBodyResponse (
    @JsonProperty("name")
    val name: String,

    @JsonProperty("version")
    val version: String,

    @JsonProperty("status")
    val status: String
) {
    enum class Status(val value: String) {
        OK("OK"),
        KO("KO")
    }
}