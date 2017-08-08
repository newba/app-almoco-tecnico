package br.com.caelum.almocotecnico.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Book
import br.com.caelum.almocotecnico.ui.adapter.BookListAdapter

/**
 * Created by alex on 08/08/17.
 */
class BooksListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_books_list, container, false) ?: super.onCreateView(inflater, container, savedInstanceState)

        val books = listOf<Book>(
                Book(id = 1, title = "Kotlin maroto", summary = "Chega de de js feio"),
                Book(id = 2, title = "Java marotinho", summary = "Prepare-se para o melhor do Java"),
                Book(id = 3, title = "C# br", summary = "noty")
        )

        val listView = view?.findViewById<ListView>(R.id.books_list_listview)

        listView?.adapter = BookListAdapter(context = context, books = books)

        return view
    }
}