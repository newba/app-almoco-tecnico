package br.com.caelum.almocotecnico.retrofit.service

import br.com.caelum.almocotecnico.model.Book
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by alex on 10/08/17.
 */
interface BookService {
    @POST("books")
    fun insert(@Body book: Book): Call<Book>

    @GET("books")
    fun all(): Call<List<Book>>
}