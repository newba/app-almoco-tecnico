package br.com.caelum.almocotecnico.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Book
import br.com.caelum.almocotecnico.retrofit.RetrofitInitializer
import br.com.caelum.almocotecnico.retrofit.callback.RetrofitCallback
import br.com.caelum.almocotecnico.ui.adapter.BookListAdapter
import br.com.caelum.almocotecnico.ui.dialog.BookDialog

/**
 * Created by alex on 08/08/17.
 */
class BooksListFragment : Fragment() {

    private val books = mutableListOf<Book>()
    private val adapter: BookListAdapter by lazy { BookListAdapter(context = context, books = books) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_books_list, container, false)
                ?: super.onCreateView(inflater, container, savedInstanceState)

        val fab = view?.findViewById<FloatingActionButton>(R.id.books_list_add)
        fab?.setOnClickListener {
            configureInsertDialog(container)
        }

        val listView = view?.findViewById<ListView>(R.id.books_list_listview)
        listView?.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        val call = RetrofitInitializer().bookService().all()
        call.enqueue(RetrofitCallback().callback { response, throwable ->
            response?.let {
                val books = response?.body()
                books?.let { updateList(books) }
            }
            throwable?.let { Log.e("fail", throwable?.message) }
        })
    }

    private fun configureInsertDialog(container: ViewGroup?) {
        container?.let {
            BookDialog(context, it).show({
                val call = RetrofitInitializer().bookService().insert(it)
                call.enqueue(RetrofitCallback().callback({ response, throwable ->
                    response?.let {
                        val book = response?.body()
                        book?.let { updateList(listOf(book)) }
                    }
                    throwable?.let { Log.i("fail", throwable.toString()) }
                }))
            })
        }
    }

    private fun updateList(book: List<Book>) {
        this.books.addAll(book)
        adapter.notifyDataSetChanged()
    }
}

