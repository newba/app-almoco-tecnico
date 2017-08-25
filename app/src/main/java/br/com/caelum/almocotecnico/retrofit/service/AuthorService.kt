package br.com.caelum.almocotecnico.retrofit.service

import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.representation.AuthorRepresentationActive
import br.com.caelum.almocotecnico.representation.AuthorRepresentationInserted
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by alex on 07/08/17.
 */
interface AuthorService {
    @GET("authors")
    fun all(): Call<List<AuthorRepresentationActive>>

    @POST("authors")
    fun insert(@Body author: Author): Call<AuthorRepresentationInserted>

    @DELETE
    fun remove(@Url url: String): Call<Void>
}