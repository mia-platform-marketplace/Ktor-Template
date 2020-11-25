package eu.miaplatform.service.controller

import com.fasterxml.jackson.databind.ObjectMapper
import eu.miaplatform.service.StatusService
import eu.miaplatform.service.model.HealthBodyResponse
import eu.miaplatform.service.module
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import org.junit.Test
import org.slf4j.event.Level
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HealthTest {
    private val objectMapper = ObjectMapper()


    @Test
    @KtorExperimentalAPI
    fun `Health should return OK`() {
        withTestApplication({
            module (
                logLevel = Level.DEBUG
            )
        }) {
            handleRequest(HttpMethod.Get, "/-/healthz") {

            }.apply {
                assertTrue { response.status()?.value == HttpStatusCode.OK.value }

                val version = StatusService().getVersion()
                val body = objectMapper.readValue(response.content, HealthBodyResponse::class.java)
                val expectedRes = HealthBodyResponse("service-api", version, HealthBodyResponse.Status.OK.value)
                assertEquals(expectedRes, body)
            }
        }
    }

    @Test
    @KtorExperimentalAPI
    fun `Ready should return OK`() {
        withTestApplication({
            module (
                logLevel = Level.DEBUG
            )
        }) {
            handleRequest(HttpMethod.Get, "/-/ready") {

            }.apply {
                assertTrue { response.status()?.value == HttpStatusCode.OK.value }

                val version = StatusService().getVersion()
                val body = objectMapper.readValue(response.content, HealthBodyResponse::class.java)
                val expectedRes = HealthBodyResponse("service-api", version, HealthBodyResponse.Status.OK.value)
                assertEquals(expectedRes, body)
            }
        }

    }

    @Test
    @KtorExperimentalAPI
    fun `Check Up should return OK`() {
        withTestApplication({
            module (
                logLevel = Level.DEBUG
            )
        }) {
            handleRequest(HttpMethod.Get, "/-/check-up") {

            }.apply {
                assertTrue { response.status()?.value == HttpStatusCode.OK.value }

                val version = StatusService().getVersion()
                val body = objectMapper.readValue(response.content, HealthBodyResponse::class.java)
                val expectedRes = HealthBodyResponse("service-api", version, HealthBodyResponse.Status.OK.value)
                assertEquals(expectedRes, body)
            }
        }

    }
}