package br.com.caelum.almocotecnico.retrofit.client

import android.util.Log
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.retrofit.RetrofitInitializer
import br.com.caelum.almocotecnico.retrofit.callback.RetrofitCallback

/**
 * Created by alex on 19/08/17.
 */
class AuthorClient {
    fun all(action: (authors: List<Author>) -> Unit) {
        val call = RetrofitInitializer().authorService().all()
        call.enqueue(RetrofitCallback().callback2(
                { response ->
                    val authors = response?.body()
                    authors?.let {
                        action(authors)
                    }
                },
                { throwable ->
                    Log.e("fail", throwable?.message)
                }))
    }
}