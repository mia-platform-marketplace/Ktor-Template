package eu.miaplatform.commons.client

import retrofit2.http.*

interface CrudClientInterface {

    @GET("v2/books")
    suspend fun getBooks(@HeaderMap headers: Map<String, String>): List<String>

}