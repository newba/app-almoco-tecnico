package br.com.caelum.almocotecnico.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.dao.AuthorDAO
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.retrofit.client.AuthorClient
import br.com.caelum.almocotecnico.ui.adapter.AuthorListAdapter
import br.com.caelum.almocotecnico.ui.dialog.AuthorDialog

/**
 * Created by alex on 08/08/17.
 */
class AuthorListFragment : Fragment() {

    private val authors = mutableListOf<Author>()
    private val authorAdapter by lazy { AuthorListAdapter(context = context, authors = authors) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val createdView = inflater?.inflate(R.layout.fragment_authors_list, container, false)
                ?: super.onCreateView(inflater, container, savedInstanceState)

        val fab = createdView?.findViewById<FloatingActionButton>(R.id.authors_list_add)
        fab?.setOnClickListener {
            configureInsertDialog(container)
        }

        configureListView(createdView)

        return createdView
    }

    private fun configureListView(createdView: View?) {
        val listView = createdView?.findViewById<ListView>(R.id.authors_list_listview)
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
                    Log.i("chega", "authorfrag")
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
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val itemPosition = menuInfo.position
        return authors[itemPosition]
    }


    override fun onResume() {
        super.onResume()
        AuthorClient().all({
            updateList(it)
        })
    }

    private fun configureInsertDialog(container: ViewGroup?) {
        container?.let {
            AuthorDialog(context = context, viewGroup = it).show({
                AuthorClient().insert(it, {
                    updateList(listOf(it))
                })
            })
        }
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