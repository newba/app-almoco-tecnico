package br.com.caelum.almocotecnico.retrofit.client

import android.util.Log
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.representation.AuthorRepresentationActive
import br.com.caelum.almocotecnico.representation.AuthorRepresentationInserted
import br.com.caelum.almocotecnico.retrofit.RetrofitInitializer
import br.com.caelum.almocotecnico.retrofit.callback.RetrofitCallback

/**
 * Created by alex on 19/08/17.
 */
class AuthorClient {

    private val authorService = RetrofitInitializer().authorService()

    fun all(action: (authors: List<Author>) -> Unit) {
        val call = authorService.all()
        call.enqueue(RetrofitCallback().callback2(
                { response ->
                    val representation = response?.body()
                    representation?.let { it ->
                        val authors = activeAuthors(it)
                        action(authors)
                    }
                },
                { throwable ->
                    Log.e("fail", throwable?.message)
                }))
    }

    fun insert(author: Author, action: (author: Author) -> Unit) {
        val call = authorService.insert(author)
        call.enqueue(RetrofitCallback().callback { response, throwable ->
            val representation = response?.body()
            representation?.let {
                val author = insertedAuthor(it)
                action(author)
            }
            throwable?.let {
                Log.e("fail", throwable.message)
            }
        })
    }

    fun remove(self: String, action: () -> Unit) {
        val call = authorService.remove(self)
        call.enqueue(RetrofitCallback().callback({ response, throwable ->
            response?.let {
                if (it.isSuccessful) {
                    action()
                }
            }
            throwable?.let {
                Log.e("fail", throwable.message)
            }
        }))
    }

    private fun insertedAuthor(representation: AuthorRepresentationInserted): Author {
        representation.author.representation.inserted = representation
        return representation.author
    }

    private fun activeAuthors(representations: List<AuthorRepresentationActive>): List<Author> {
        representations.forEach { it.author.representation.active = it }
        return representations.map { it.author }
    }
}
