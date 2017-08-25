package br.com.caelum.almocotecnico.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.dao.AuthorDAO
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.retrofit.client.AuthorClient
import br.com.caelum.almocotecnico.ui.adapter.AuthorListAdapter
import br.com.caelum.almocotecnico.ui.dialog.AuthorDialog
import kotlinx.android.synthetic.main.fragment_authors_list.view.*

/**
 * Created by alex on 08/08/17.
 */
class AuthorListFragment : Fragment() {

    private val authors = mutableListOf<Author>()
    private val authorAdapter by lazy { AuthorListAdapter(context = context, authors = authors) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val createdView = inflater?.inflate(R.layout.fragment_authors_list, container, false)

        createdView?.let { view ->
            configureListView(view)
            container?.let { viewGroup ->
                configureFab(view, viewGroup)
            }
        }

        return createdView
    }

    private fun configureFab(view: View, viewGroup: ViewGroup) {
        val fab = view.authors_list_add
        fab.setOnClickListener {
            configureInsertDialog(viewGroup)
        }
    }

    private fun configureListView(createdView: View) {
        val listView = createdView.authors_list_listview
        listView?.let {
            with(it) {
                adapter = authorAdapter
                setOnCreateContextMenuListener { contextMenu, _, _ ->
                    contextMenu.add(Menu.NONE, 1, Menu.NONE, "Remove")
                }
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        item?.let {
            val itemId = it.itemId
            when (itemId) {
                1 -> {
                    val author = authorClickedByContextMenu(it)
                    val self = author.representation.self()
                    AuthorClient().remove(self, {
                        remove(author)
                    })
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun remove(author: Author) {
        AuthorDAO().remove(author)
        authors.remove(author)
        updateList()
    }


    private fun authorClickedByContextMenu(item: MenuItem): Author {
        val menuInfo = item.menuInfo
        if (menuInfo is AdapterView.AdapterContextMenuInfo) {
            return authors[menuInfo.position]
        }
        return throw IllegalArgumentException("invalid menuItem")
    }


    override fun onResume() {
        super.onResume()
        AuthorClient().all({
            updateList(it)
        })
    }

    private fun configureInsertDialog(viewGroup: ViewGroup) {
        AuthorDialog(context = context, viewGroup = viewGroup)
                .show({
                    AuthorClient().insert(it, {
                        updateList(listOf(it))
                    })
                })
    }


    private fun updateList(authors: List<Author>) {
        AuthorDAO().add(authors)
        this.authors.addAll(authors)
        updateList()
    }

    private fun updateList() {
        authorAdapter.notifyDataSetChanged()
    }
}