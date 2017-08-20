package br.com.caelum.almocotecnico.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Book
import br.com.caelum.almocotecnico.retrofit.client.BookClient
import br.com.caelum.almocotecnico.ui.adapter.BookListAdapter
import br.com.caelum.almocotecnico.ui.dialog.BookDialog

/**
 * Created by alex on 08/08/17.
 */
class BooksListFragment : Fragment() {

    private val books = mutableListOf<Book>()
    private val bookAdapter: BookListAdapter by lazy { BookListAdapter(context = context, books = books) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_books_list, container, false)
                ?: super.onCreateView(inflater, container, savedInstanceState)
        configureFab(view, container)
        configureListView(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        BookClient().all {
            update(it)
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        item?.let {
            val itemId = item.itemId
            if (itemId == 1) {
                val book = bookClickedByContextMenu(item)
                val self = book.representation.self()
                if (self.isNotEmpty()) {
                    BookClient().remove(self, {
                        remove(book)
                    })
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun bookClickedByContextMenu(item: MenuItem): Book {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val itemPosition = menuInfo.position
        val book = books[itemPosition]
        return book
    }

    private fun configureFab(view: View?, container: ViewGroup?) {
        val fab = view?.findViewById<FloatingActionButton>(R.id.books_list_add)
        fab?.setOnClickListener {
            configureInsertDialog(container)
        }
    }

    private fun configureListView(view: View?) {
        val listView = view?.findViewById<ListView>(R.id.books_list_listview)
        listView?.let {
            with(it) {
                adapter = bookAdapter
                setOnCreateContextMenuListener { contextMenu, _, _ ->
                    contextMenu.add(Menu.NONE, 1, Menu.NONE, "Remove")
                }
            }
        }
    }

    private fun configureInsertDialog(container: ViewGroup?) {
        container?.let {
            BookDialog(context, it).show({
                BookClient().insert(it, {
                    update(listOf(it))
                })
            })
        }
    }

    private fun update(book: List<Book>) {
        this.books.addAll(book)
        bookAdapter.notifyDataSetChanged()
    }

    private fun remove(book: Book) {
        this.books.remove(book)
        bookAdapter.notifyDataSetChanged()
    }
}

