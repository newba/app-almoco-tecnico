package br.com.caelum.almocotecnico.retrofit.service

import br.com.caelum.almocotecnico.model.Book
    import br.com.caelum.almocotecnico.representation.BookRepresentationActive
    import br.com.caelum.almocotecnico.representation.BookRepresentationInserted
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by alex on 10/08/17.
 */
interface BookService {
    @POST("books")
    fun insert(@Body book: Book): Call<BookRepresentationInserted>

    @GET("books")
    fun all(): Call<List<BookRepresentationActive>>

    @DELETE
    fun remove(@Url url: String): Call<Void>
}