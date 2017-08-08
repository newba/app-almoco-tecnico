package br.com.caelum.almocotecnico.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.model.Book


/**
 * Created by alex on 07/08/17.
 */
class AuthorListAdapter(
        private val context: Context,
        private val authors: List<Author>) : BaseAdapter() {


    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.authors_item, viewGroup, false)
        val author = authors.get(position)
        val bookContent = view.findViewById<TextView>(R.id.authors_item_content)
        bookContent.text = "${author.id} - ${author.name}"
        return view
    }

    override fun getItem(position: Int): Author {
        return authors.get(0)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getCount(): Int {
        return authors.size
    }

}