package br.com.caelum.almocotecnico.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Book


/**
 * Created by alex on 07/08/17.
 */
class BookListAdapter(
        private val context: Context,
        private val books: List<Book>) : BaseAdapter() {


    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.book_item, viewGroup, false)
        val book = books.get(position)
        val bookContent = view.findViewById<TextView>(R.id.book_item_content)
        bookContent.text = book.title
        return view
    }

    override fun getItem(position: Int): Book {
        return books.get(0)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getCount(): Int {
        return books.size
    }

}