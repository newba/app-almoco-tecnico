package br.com.caelum.almocotecnico.retrofit

import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.retrofit.service.AuthorService
import br.com.caelum.almocotecnico.retrofit.service.BookService
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by alex on 07/08/17.
 */
class RetrofitInitializer {

    private val endPoint = "http://192.168.0.42:8080/"

    private val retrofit: Retrofit by lazy {
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

    fun bookService(): BookService {
        return retrofit.create(BookService::class.java)
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
        objectMapper.registerKotlinModule()
        return objectMapper
    }
}