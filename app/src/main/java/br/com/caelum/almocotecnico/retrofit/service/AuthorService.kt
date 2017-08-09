package br.com.caelum.almocotecnico.retrofit.service

import br.com.caelum.almocotecnico.model.Author
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by alex on 07/08/17.
 */
interface AuthorService {

    @GET("authors/{id}")
    fun find(@Path("id") id: Long): Call<Author>

    @GET("authors")
    fun all(): Call<List<Author>>

    @POST("authors")
    fun insert(@Body author: Author): Call<Author>
}