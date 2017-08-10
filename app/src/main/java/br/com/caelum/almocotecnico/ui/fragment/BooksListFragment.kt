package br.com.caelum.almocotecnico.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.dao.AuthorDAO
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.model.Book
import br.com.caelum.almocotecnico.retrofit.RetrofitInitializer
import br.com.caelum.almocotecnico.ui.adapter.BookListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by alex on 08/08/17.
 */
class BooksListFragment : Fragment() {

    val books = mutableListOf<Book>()
    val adapter: BookListAdapter by lazy { BookListAdapter(context = context, books = books) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_books_list, container, false) ?: super.onCreateView(inflater, container, savedInstanceState)

        val listView = view?.findViewById<ListView>(R.id.books_list_listview)
        val fab = view?.findViewById<FloatingActionButton>(R.id.books_list_add)

        fab?.setOnClickListener {

            val createdView = LayoutInflater.from(context).inflate(R.layout.add_book, container, false)

            val authors = AuthorDAO().all()
            val arrayBooks = authors.map { it.name }
            val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, arrayBooks)
            val spinner = createdView.findViewById<Spinner>(R.id.add_book_author_spinner)
            spinner.adapter = adapter

            AlertDialog.Builder(context)
                    .setTitle("Add Book")
                    .setView(createdView)
                    .setPositiveButton("Send", { _: DialogInterface, _: Int ->
                        val titleField = createdView.findViewById<EditText>(R.id.add_book_title)
                        val summaryField = createdView.findViewById<EditText>(R.id.add_book_summary)
                        val authorField = createdView.findViewById<Spinner>(R.id.add_book_author_spinner)

                        val title = titleField.text.toString()
                        val summary = summaryField.text.toString()
                        val author = authors.get(authorField.selectedItemPosition)

                        val book = Book(title = title, summary = summary, authors = listOf(author))

                        val call = RetrofitInitializer().bookService().insert(book)


                        call.enqueue(object : Callback<Book?> {
                            override fun onResponse(call: Call<Book?>?, response: Response<Book?>?) {
                                val book = response?.body()
                                book?.let { updateList(listOf(book)) }

                            }

                            override fun onFailure(call: Call<Book?>?, t: Throwable?) {
                                Log.e("fail", t?.message)
                            }
                        })
                        Log.i("book", book.toString())
                    })
                    .show()

        }

        listView?.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        val call = RetrofitInitializer().bookService().all()
        call.enqueue(object : Callback<List<Book>?> {
            override fun onFailure(call: Call<List<Book>?>?, t: Throwable?) {
                Log.e("fail", t?.message)
            }

            override fun onResponse(call: Call<List<Book>?>?, response: Response<List<Book>?>?) {
                val books = response?.body()
                books?.let { updateList(books) }
            }
        })
    }

    private fun updateList(book: List<Book>) {
        this.books.addAll(book)
        adapter.notifyDataSetChanged()
    }
}

