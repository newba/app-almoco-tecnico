package br.com.caelum.almocotecnico.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Author

/**
 * Created by alex on 16/08/17.
 */
class AuthorDialog(private val context: Context,
                   private val viewGroup: ViewGroup) {
    val createdView by lazy { LayoutInflater.from(context).inflate(R.layout.add_author, viewGroup, false) }
    val fieldName by lazy { createdView.findViewById<EditText>(R.id.add_author_name) }


    fun show(action: (Author) -> Unit) {
        AlertDialog.Builder(context)
                .setTitle("Add Author")
                .setView(createdView)
                .setPositiveButton("Save", { _, _ ->
                    val author = Author(name = fieldName.text.toString())
                    action(author)
                }).show()
    }


}