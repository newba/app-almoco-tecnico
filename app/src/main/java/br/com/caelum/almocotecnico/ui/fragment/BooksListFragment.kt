package br.com.caelum.almocotecnico.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Book
import br.com.caelum.almocotecnico.retrofit.client.BookClient
import br.com.caelum.almocotecnico.ui.adapter.BookListAdapter
import br.com.caelum.almocotecnico.ui.dialog.BookDialog
import kotlinx.android.synthetic.main.fragment_books_list.view.*

/**
 * Created by alex on 08/08/17.
 */
class BooksListFragment : Fragment() {

    private val books = mutableListOf<Book>()
    private val bookAdapter: BookListAdapter by lazy { BookListAdapter(context = context, books = books) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val createdView = inflater?.inflate(R.layout.fragment_books_list, container, false)
        createdView?.let { view ->
            container?.let { viewGroup ->
                configureFab(view, viewGroup)
            }
            configureListView(view)
        }
        return createdView
    }

    override fun onResume() {
        super.onResume()
        BookClient().all {
            update(it)
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        item?.let {
            val itemId = it.itemId
            if (itemId == 2) {
                val book = bookClickedByContextMenu(it)
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
        return books[itemPosition]
    }

    private fun configureFab(view: View, container: ViewGroup) {
        val fab = view.books_list_add
        fab.setOnClickListener {
            configureInsertDialog(container)
        }
    }

    private fun configureListView(view: View?) {
        val listView = view?.findViewById<ListView>(R.id.books_list_listview)
        listView?.let { lv ->
            with(lv) {
                adapter = bookAdapter
                setOnCreateContextMenuListener { contextMenu, _, _ ->
                    contextMenu.add(Menu.NONE, 2, Menu.NONE, "Remove")
                }
            }
        }
    }

    private fun configureInsertDialog(viewGroup: ViewGroup) {
        BookDialog(context, viewGroup)
            .show({ returnedBook ->
                BookClient().insert(returnedBook, { savedBook ->
                    update(listOf(savedBook))
                })
            })
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

