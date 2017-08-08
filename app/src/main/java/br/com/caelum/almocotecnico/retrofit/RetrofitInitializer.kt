package br.com.caelum.almocotecnico.retrofit

import br.com.caelum.almocotecnico.retrofit.service.AuthorService
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by alex on 07/08/17.
 */
class RetrofitInitializer {

    private val endPoint = "http://192.168.65.39:8080/"

    val retrofit: Retrofit by lazy {
        val objectMapper = configureJackson()
        var client = configureOkHttpClient()
        Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build()
    }

    fun authorService(): AuthorService {
        return retrofit.create(AuthorService::class.java)
    }

    private fun configureOkHttpClient(): OkHttpClient? {
        val interceptor = configureLoggingInterceptor()
        var client = OkHttpClient.Builder()
                .addInterceptor(interceptor).build()
        return client
    }

    private fun configureLoggingInterceptor(): HttpLoggingInterceptor {
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = BODY
        return interceptor
    }

    private fun configureJackson(): ObjectMapper {
        var objectMapper = ObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return objectMapper
    }
}