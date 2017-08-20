package br.com.caelum.almocotecnico.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val adapter by lazy { AuthorListAdapter(context = context, authors = authors) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val createdView = inflater?.inflate(R.layout.fragment_authors_list, container, false)
                ?: super.onCreateView(inflater, container, savedInstanceState)

        val fab = createdView?.findViewById<FloatingActionButton>(R.id.authors_list_add)
        fab?.setOnClickListener {
            configureInsertDialog(container)
        }

        val listView = createdView?.findViewById<ListView>(R.id.authors_list_listview)
        listView?.adapter = adapter

        return createdView
    }

    override fun onResume() {
        super.onResume()
        AuthorClient().all({
            AuthorDAO().add(it)
            updateList(it)
        })
    }

    private fun configureInsertDialog(container: ViewGroup?) {
        container?.let {
            AuthorDialog(context = context, viewGroup = it).show({
                AuthorClient().insert(it, {
                    AuthorDAO().add(it)
                    updateList(listOf(it))
                })
            })
        }
    }

    private fun updateList(authors: List<Author>) {
        this.authors.addAll(authors)
        adapter.notifyDataSetChanged()
    }
}