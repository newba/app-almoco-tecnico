package br.com.caelum.almocotecnico.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.ui.adapter.AuthorListAdapter
import kotlinx.android.synthetic.main.authors_item.*

/**
 * Created by alex on 08/08/17.
 */
class AuthorListFragment() : Fragment() {


    val authors = listOf<Author>(
            Author(id = 1, name = "Alex"),
            Author(id = 2, name = "Felipe"))

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_authors_list, container, false) ?: super.onCreateView(inflater, container, savedInstanceState)

        val listView = view?.findViewById<ListView>(R.id.authors_list_listview)

        listView?.adapter = AuthorListAdapter(context = context, authors = authors)

        return view
    }
}