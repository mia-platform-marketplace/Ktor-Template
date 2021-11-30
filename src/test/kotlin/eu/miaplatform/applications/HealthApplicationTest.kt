package eu.miaplatform.applications

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.databind.ObjectMapper
import eu.miaplatform.baseModule
import eu.miaplatform.core.ktor.install
import eu.miaplatform.model.response.health.HealthBodyResponse
import eu.miaplatform.services.StatusService
import io.kotest.core.spec.style.DescribeSpec
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication

class HealthApplicationTest : DescribeSpec ({
    val objectMapper = ObjectMapper()

    describe("/-/health") {
        it("should return OK") {
            withTestApplication({
                baseModule()
                install(HealthApplication())
            }) {
                handleRequest(HttpMethod.Get, "/-/healthz").apply {
                    assertThat(response.status()?.value).isEqualTo(HttpStatusCode.OK.value)

                    val version = StatusService().getVersion()
                    val body = objectMapper.readValue(response.content, HealthBodyResponse::class.java)
                    val expectedRes = HealthBodyResponse("service-api", version, HealthBodyResponse.Status.OK.value)
                    assertThat(body).isEqualTo(expectedRes)
                }
            }
        }
    }

    describe("/-/ready") {
        it("should return OK") {
            withTestApplication({
                baseModule()
                install(HealthApplication())
            }) {
                handleRequest(HttpMethod.Get, "/-/ready").apply {
                    assertThat(response.status()?.value).isEqualTo(HttpStatusCode.OK.value)

                    val version = StatusService().getVersion()
                    val body = objectMapper.readValue(response.content, HealthBodyResponse::class.java)
                    val expectedRes = HealthBodyResponse("service-api", version, HealthBodyResponse.Status.OK.value)
                    assertThat(body).isEqualTo(expectedRes)
                }
            }
        }
    }

    describe("/-/check-up") {
        it("should return OK") {
            withTestApplication({
                baseModule()
                install(HealthApplication())
            }) {
                handleRequest(HttpMethod.Get, "/-/check-up").apply {
                    assertThat(response.status()?.value).isEqualTo(HttpStatusCode.OK.value)

                    val version = StatusService().getVersion()
                    val body = objectMapper.readValue(response.content, HealthBodyResponse::class.java)
                    val expectedRes = HealthBodyResponse("service-api", version, HealthBodyResponse.Status.OK.value)
                    assertThat(body).isEqualTo(expectedRes)
                }
            }
        }
    }
})