package br.com.caelum.almocotecnico.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.dao.AuthorDAO
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.model.Book

/**
 * Created by alex on 16/08/17.
 */
class BookDialog(private val context: Context,
                 private val viewGroup: ViewGroup) {
    val createdView by lazy { LayoutInflater.from(context).inflate(R.layout.add_book, viewGroup, false) }


    fun show(action: (Book) -> Unit) {

        val authors = configureSpinner()

        AlertDialog.Builder(context)
                .setTitle("Add Book")
                .setView(createdView)
                .setPositiveButton("Send", { _: DialogInterface, _: Int ->
                    val titleField = createdView.findViewById<EditText>(R.id.add_book_title)
                    val summaryField = createdView.findViewById<EditText>(R.id.add_book_summary)
                    val authorField = createdView.findViewById<Spinner>(R.id.add_book_author_spinner)

                    val title = titleField.text.toString()
                    val summary = summaryField.text.toString()
                    val author = authors.get(authorField.selectedItemPosition)
                    Log.i("author selected", author.toString())

                    val book = Book(title = title, summary = summary, authors = listOf(author))

                    action(book)
                })
                .show()
    }

    private fun configureSpinner(): List<Author> {
        val authors = AuthorDAO().all()
        Log.i("author dao", authors.toString())
        val arrayBooks = authors.map { it.name }
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, arrayBooks)
        val spinner = createdView.findViewById<Spinner>(R.id.add_book_author_spinner)
        spinner.adapter = adapter
        return authors
    }


}