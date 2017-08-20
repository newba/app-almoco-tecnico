package br.com.caelum.almocotecnico.retrofit.client

import android.util.Log
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.model.Book
import br.com.caelum.almocotecnico.retrofit.RetrofitInitializer
import br.com.caelum.almocotecnico.retrofit.callback.RetrofitCallback

/**
 * Created by alex on 19/08/17.
 */
class BookClient {

    private val bookService = RetrofitInitializer().bookService()

    fun all(action: (List<Book>) -> Unit) {
        val call = bookService.all()
        call.enqueue(RetrofitCallback().callback { response, throwable ->
            response?.let {
                val representations = response?.body()
                representations?.let {
                    representations.forEach { it.book.representation.active = it }
                    val books = representations.map { it.book }.toMutableList().toList()
                    action(books)
                }
            }
            throwable?.let { defaultFailMessage(throwable) }
        })
    }

    fun insert(book: Book, action: (Book) -> Unit) {
        val call = bookService.insert(book)
        call.enqueue(RetrofitCallback().callback({ response, throwable ->
            response?.let {
                val representation = response?.body()
                representation?.let {
                    it.book.representation.inserted = it
                    val bookInserted = it.book
                    bookInserted?.let { action(it) }
                    val author = book.authors.first()

                    bindBookAndAuthor(bookInserted, author)
                }
            }
            throwable?.let {
                defaultFailMessage(it)
            }
        }))
    }

    private fun bindBookAndAuthor(bookInserted: Book, author: Author) {
        val bindBookAndAuthor = bookInserted.representation.self() + author.representation.self()
        val call = bookService.bindAuthor(bindBookAndAuthor)
        call.enqueue(RetrofitCallback().callback({ response, throwable ->
            response?.let {
                Log.i("bind", "success")
            }
            throwable?.let {
                defaultFailMessage(it)
            }
        }))
    }

    fun remove(url: String, action: () -> Unit) {
        val call = bookService.remove(url)
        call.enqueue(RetrofitCallback().callback({ response, throwable ->
            response?.let {
                action()
            }
            throwable?.let {
                defaultFailMessage(it)
            }
        }))
    }

    private fun defaultFailMessage(throwable: Throwable) = Log.e("fail", throwable.message)
}