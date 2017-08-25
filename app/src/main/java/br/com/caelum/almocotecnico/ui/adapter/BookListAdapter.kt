package br.com.caelum.almocotecnico.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Book
import kotlinx.android.synthetic.main.book_item.view.*


/**
 * Created by alex on 07/08/17.
 */
class BookListAdapter(
        private val context: Context,
        private val books: List<Book>) : BaseAdapter() {


    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val createView = LayoutInflater.from(context).inflate(R.layout.book_item, viewGroup, false)
        val book = books[position]
        createView.book_item_content.text = book.title
        return createView
    }

    override fun getItem(position: Int): Book {
        return books[0]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return books.size
    }

}