package eu.miaplatform.service.client

import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class HeadersToProxyTest {

    @Test
    fun `Returns empty header map when no header is present`() {

        val headersToProxy = HeadersToProxy()

        withTestApplication {

            handleRequest(HttpMethod.Get, "/proxy-headers"){
            }.apply {
                val headers = headersToProxy.proxy(this)

                assertEquals(mapOf(), headers)
            }
        }
    }

    @Test
    fun `Returns the correct header map when x-request-id has a value`() {

        val headersToProxy = HeadersToProxy()

        withTestApplication {

            handleRequest(HttpMethod.Get, "/proxy-headers"){
                addHeader("x-request-id", "1234abcd")
            }.apply {
                val headers = headersToProxy.proxy(this)

                assertEquals(mapOf("x-request-id" to "1234abcd"), headers)
            }
        }
    }

    @Test
    fun `Returns the correct header map when miauserid has a value`() {

        val headersToProxy = HeadersToProxy()

        withTestApplication {

            handleRequest(HttpMethod.Get, "/proxy-headers"){
                addHeader("miauserid", "userid")
            }.apply {
                val headers = headersToProxy.proxy(this)

                assertEquals(mapOf("miauserid" to "userid"), headers)
            }
        }
    }

    @Test
    fun `Returns the correct header map when miausergroups has a value`() {

        val headersToProxy = HeadersToProxy()

        withTestApplication {

            handleRequest(HttpMethod.Get, "/proxy-headers"){
                addHeader("miausergroups", "group")
            }.apply {
                val headers = headersToProxy.proxy(this)

                assertEquals(mapOf("miausergroups" to "group"), headers)
            }
        }
    }

    @Test
    fun `Returns the correct header map when client-type has a value`() {

        val headersToProxy = HeadersToProxy()

        withTestApplication {

            handleRequest(HttpMethod.Get, "/proxy-headers"){
                addHeader("client-type", "type")
            }.apply {
                val headers = headersToProxy.proxy(this)

                assertEquals(mapOf("client-type" to "type"), headers)
            }
        }
    }

    @Test
    fun `Returns the correct header map when isbackoffice has a value`() {

        val headersToProxy = HeadersToProxy()

        withTestApplication {

            handleRequest(HttpMethod.Get, "/proxy-headers"){
                addHeader("isbackoffice", "true")
            }.apply {
                val headers = headersToProxy.proxy(this)

                assertEquals(mapOf("isbackoffice" to "true"), headers)
            }
        }
    }

    @Test
    fun `Returns the correct header map when miauserproperties has a value`() {

        val headersToProxy = HeadersToProxy()

        withTestApplication {

            handleRequest(HttpMethod.Get, "/proxy-headers"){
                addHeader("miauserproperties", "property")
            }.apply {
                val headers = headersToProxy.proxy(this)

                assertEquals(mapOf("miauserproperties" to "property"), headers)
            }
        }
    }

    @Test
    fun `Returns the correct header map when all platform headers have value`() {

        val headersToProxy = HeadersToProxy()

        withTestApplication {

            handleRequest(HttpMethod.Get, "/proxy-headers"){
                addHeader("x-request-id", "1234abcd")
                addHeader("miauserid", "userid")
                addHeader("miausergroups", "group")
                addHeader("miauserproperties", "property")
                addHeader("client-type", "type")
                addHeader("miauserproperties", "property")
            }.apply {
                val headers = headersToProxy.proxy(this)

                assertEquals(
                    mapOf(
                        "x-request-id" to "1234abcd",
                        "miauserid" to "userid",
                        "miausergroups" to "group",
                        "miauserproperties" to "property",
                        "client-type" to "type",
                        "miauserproperties" to "property"
                    ), headers)
            }
        }
    }

    @Test
    fun `Returns the header map with only headers to proxy when there are more`() {

        val headersToProxy = HeadersToProxy()

        withTestApplication {

            handleRequest(HttpMethod.Get, "/proxy-headers"){
                addHeader("x-request-id", "1234abcd")
                addHeader("miauserid", "userid")
                addHeader("miausergroups", "group")
                addHeader("miauserproperties", "property")
                addHeader("client-type", "type")
                addHeader("miauserproperties", "property")
                addHeader("some-other-header", "other")
            }.apply {
                val headers = headersToProxy.proxy(this)

                assertEquals(
                    mapOf(
                        "x-request-id" to "1234abcd",
                        "miauserid" to "userid",
                        "miausergroups" to "group",
                        "miauserproperties" to "property",
                        "client-type" to "type",
                        "miauserproperties" to "property"
                    ), headers)
            }
        }
    }

    @Test
    fun `Returns the header map with additional headers to proxy if present`() {

        val headersToProxy = HeadersToProxy("some-other-header-to-proxy")

        withTestApplication {

            handleRequest(HttpMethod.Get, "/proxy-headers"){
                addHeader("x-request-id", "1234abcd")
                addHeader("miauserid", "userid")
                addHeader("miausergroups", "group")
                addHeader("miauserproperties", "property")
                addHeader("client-type", "type")
                addHeader("miauserproperties", "property")
                addHeader("some-other-header-to-proxy", "other")
            }.apply {
                val headers = headersToProxy.proxy(this)

                System.getProperty("ADDITIONAL_HEADERS_TO_PROXY", "some-other-header-to-proxy")
                assertEquals(
                    mapOf(
                        "x-request-id" to "1234abcd",
                        "miauserid" to "userid",
                        "miausergroups" to "group",
                        "miauserproperties" to "property",
                        "client-type" to "type",
                        "miauserproperties" to "property",
                        "some-other-header-to-proxy" to "other"
                    ), headers)
            }
        }
    }
}