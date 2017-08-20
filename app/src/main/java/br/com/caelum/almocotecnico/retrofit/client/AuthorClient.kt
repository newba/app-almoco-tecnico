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
                    val representation = response?.body()
                    representation?.let {
                        it.forEach { it.author.representation.active = it }
                        val authors = it.map { it.author }
                        authors?.let {
                            Log.i("authors", authors.toString())
                            action(authors)
                        }
                    }
                },
                { throwable ->
                    Log.e("fail", throwable?.message)
                }))
    }

    fun insert(author: Author, action: (author: Author) -> Unit) {
        val call = RetrofitInitializer().authorService().insert(author)
        call.enqueue(RetrofitCallback().callback { response, throwable ->
            val representation = response?.body()
            representation?.let {
                it.author.representation.inserted = it
                val insertedAuthor = it.author
                action(insertedAuthor)
            }
            throwable?.let {
                Log.e("fail", throwable.message)
            }
        })
    }
}