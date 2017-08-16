package br.com.caelum.almocotecnico.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.dao.AuthorDAO
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.retrofit.RetrofitInitializer
import br.com.caelum.almocotecnico.retrofit.callback.RetrofitCallback
import br.com.caelum.almocotecnico.ui.adapter.AuthorListAdapter

/**
 * Created by alex on 08/08/17.
 */
class AuthorListFragment : Fragment() {

    val authors = mutableListOf<Author>()

    private val adapter by lazy { AuthorListAdapter(context = context, authors = authors) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val createdView = inflater?.inflate(R.layout.fragment_authors_list, container, false)
                ?: super.onCreateView(inflater, container, savedInstanceState)

        val listView = createdView?.findViewById<ListView>(R.id.authors_list_listview)
        val fab = createdView?.findViewById<FloatingActionButton>(R.id.authors_list_add)

        fab?.setOnClickListener {

            val createdView = LayoutInflater.from(context).inflate(R.layout.add_author, container, false)
            val fieldName = createdView.findViewById<EditText>(R.id.add_author_name)

            AlertDialog.Builder(context)
                    .setTitle("Add Author")
                    .setView(createdView)
                    .setPositiveButton("Save", { _, _ ->
                        val author = Author(name = fieldName.text.toString())
                        val call = RetrofitInitializer().authorService().insert(author)
                        call.enqueue(RetrofitCallback().callback { response, throwable ->
                            response?.let {
                                AuthorDAO().add(author)
                                updateList(listOf(author))
                            }
                            throwable?.let {
                                Log.e("fail", throwable.message)
                            }
                        })
                    }).show()
        }

        listView?.adapter = adapter

        return createdView
    }

    override fun onResume() {
        super.onResume()
        val call = RetrofitInitializer().authorService().all()
        call.enqueue(RetrofitCallback().callback2(
                { response ->
                    val authors = response?.body()
                    authors?.let {
                        AuthorDAO().add(authors)
                        updateList(authors)
                    }
                },
                { throwable ->
                    Log.e("fail", throwable?.message)
                }))
    }

    private fun updateList(authors: List<Author>) {
        this.authors.addAll(authors)
        adapter.notifyDataSetChanged()
    }
}