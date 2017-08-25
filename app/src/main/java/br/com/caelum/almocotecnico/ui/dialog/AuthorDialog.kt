package br.com.caelum.almocotecnico.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Author
import kotlinx.android.synthetic.main.form_author.view.*

/**
 * Created by alex on 16/08/17.
 */
class AuthorDialog(private val context: Context,
                   private val viewGroup: ViewGroup) {

    private val createdView by lazy {
        LayoutInflater.from(context).inflate(R.layout.form_author, viewGroup, false)
    }

    fun show(action: (Author) -> Unit) {
        AlertDialog.Builder(context)
                .setTitle("Add Author")
                .setView(createdView)
                .setPositiveButton("Save", { _, _ ->
                    val author = Author(name = createdView.form_author_name.text.toString())
                    action(author)
                }).show()
    }


}