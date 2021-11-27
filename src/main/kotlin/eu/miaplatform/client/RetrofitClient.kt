package eu.miaplatform.client

import com.fasterxml.jackson.databind.ObjectMapper
import eu.miaplatform.core.Serialization
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    inline fun <reified T> build(
        basePath: String,
        logLevel: HttpLoggingInterceptor.Level,
        objectMapper: ObjectMapper = Serialization.defaultRetrofitMapper
    ): T {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = logLevel
        }

        val client = OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(basePath)
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
            .create(T::class.java)
    }
}