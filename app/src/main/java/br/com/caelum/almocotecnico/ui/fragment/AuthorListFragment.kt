package br.com.caelum.almocotecnico.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.retrofit.RetrofitInitializer
import br.com.caelum.almocotecnico.ui.adapter.AuthorListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by alex on 08/08/17.
 */
class AuthorListFragment() : Fragment() {


    val authors = mutableListOf<Author>()

    private val adapter by lazy { AuthorListAdapter(context = context, authors = authors) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_authors_list, container, false) ?: super.onCreateView(inflater, container, savedInstanceState)

        val listView = view?.findViewById<ListView>(R.id.authors_list_listview)
        val fab = view?.findViewById<FloatingActionButton>(R.id.authors_list_add)

        fab?.setOnClickListener {

            val createdView = LayoutInflater.from(context).inflate(R.layout.add_author, container, false)
            val fieldName = createdView.findViewById<EditText>(R.id.add_author_name)

            AlertDialog.Builder(context)
                    .setTitle("Add Author")
                    .setView(createdView)
                    .setPositiveButton("Save", { dialogInterface, i ->
                        val author = Author(name = fieldName.text.toString())
                        val call = RetrofitInitializer().authorService().insert(author)
                        call.enqueue(object : Callback<Author?> {
                            override fun onFailure(call: Call<Author?>?, t: Throwable?) {
                                Log.e("fail", t?.message)
                            }

                            override fun onResponse(call: Call<Author?>?, response: Response<Author?>?) {
                                val author = response?.body()
                                author?.let { updateList(listOf<Author>(author)) }
                            }
                        })
                    }).show()
        }

        listView?.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        val call = RetrofitInitializer().authorService().all()
        call.enqueue(object : Callback<List<Author>> {
            override fun onFailure(call: Call<List<Author>>?, t: Throwable?) {
                Log.e("fail", t?.message)
            }

            override fun onResponse(call: Call<List<Author>>?, response: Response<List<Author>>?) {
                val authors = response?.body()
                authors?.let { updateList(authors) }
            }
        })
    }

    private fun updateList(authors: List<Author>) {
        this.authors.addAll(authors)
        adapter.notifyDataSetChanged()
    }
}