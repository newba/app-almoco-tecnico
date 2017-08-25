package br.com.caelum.almocotecnico.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Author
import kotlinx.android.synthetic.main.authors_item.view.*


/**
 * Created by alex on 07/08/17.
 */
class AuthorListAdapter(
        private val context: Context,
        private val authors: List<Author>) : BaseAdapter() {


    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val createdView = LayoutInflater.from(context)
                .inflate(R.layout.authors_item, viewGroup, false)
        val author = authors[position]
        createdView.authors_item_content.text = author.name
        return createdView
    }

    override fun getItem(position: Int): Author {
        return authors[0]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return authors.size
    }

}