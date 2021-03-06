package br.com.caelum.almocotecnico.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.dao.AuthorDAO
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.model.Book
import kotlinx.android.synthetic.main.form_book.view.*

/**
 * Created by alex on 16/08/17.
 */
class BookDialog(private val context: Context,
                 private val viewGroup: ViewGroup) {

    private val createdView by lazy {
        LayoutInflater.from(context).inflate(R.layout.form_book, viewGroup, false)
    }

    private val authorField by lazy {
        createdView.add_book_author_spinner
    }

    fun show(action: (Book) -> Unit) {
        val authors = configureSpinner()
        AlertDialog.Builder(context)
                .setTitle("Add Book")
                .setView(createdView)
                .setPositiveButton("Send", { _: DialogInterface, _: Int ->
                    val titleField = createdView.add_book_title
                    val summaryField = createdView.form_book_summary

                    val title = titleField.text.toString()
                    val summary = summaryField.text.toString()
                    val author = authors[authorField.selectedItemPosition]

                    val book = Book(title = title, summary = summary, authors = listOf(author))
                    action(book)
                })
                .show()
    }

    private fun configureSpinner(): List<Author> {
        val authors = AuthorDAO().all()
        val arrayBooks = authors.map { it.name }
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, arrayBooks)
        authorField.adapter = adapter
        return authors
    }


}