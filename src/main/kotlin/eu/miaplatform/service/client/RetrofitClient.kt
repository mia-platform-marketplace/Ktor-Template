package eu.miaplatform.service.client

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient<T> (private val basePath: String, private val logLevel: HttpLoggingInterceptor.Level, private val clazz: Class<T>) {

    private var restInterface: T? = null

    fun getRestClient(): T {
        if (restInterface == null) {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = logLevel

            val client = OkHttpClient.Builder()
                .callTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build()

            val mapper = ObjectMapper().apply {
                setSerializationInclusion(JsonInclude.Include.NON_NULL)
            }

            restInterface = Retrofit.Builder()
                .baseUrl(basePath)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build()
                .create(clazz)
        }
        return restInterface!!
    }
}