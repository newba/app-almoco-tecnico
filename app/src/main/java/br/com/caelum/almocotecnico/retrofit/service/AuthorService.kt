package br.com.caelum.almocotecnico.retrofit.service

import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.representation.AuthorRepresentationActive
import br.com.caelum.almocotecnico.representation.AuthorRepresentationInserted
import br.com.caelum.almocotecnico.representation.BookRepresentationActive
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by alex on 07/08/17.
 */
interface AuthorService {
    @GET("authors")
    fun all(): Call<List<AuthorRepresentationActive>>

    @POST("authors")
    fun insert(@Body author: Author): Call<AuthorRepresentationInserted>
}