package br.com.caelum.almocotecnico.retrofit.service

import br.com.caelum.almocotecnico.model.Author
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by alex on 07/08/17.
 */
interface AuthorService {

    @GET("authorsTitle/{id}")
    fun find(@Path("id") id: Long): Call<Author>
}