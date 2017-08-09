package br.com.caelum.almocotecnico.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.model.Author
import br.com.caelum.almocotecnico.retrofit.RetrofitInitializer
import br.com.caelum.almocotecnico.ui.fragment.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupTabs()
        doRequest()
    }

    private fun setupTabs() {
        var mainPagerAdapter = MainPagerAdapter(supportFragmentManager)
        main_viewpager.adapter = mainPagerAdapter
        main_sliding_tabs.setupWithViewPager(main_viewpager)
    }

    private fun doRequest() {
        var call = RetrofitInitializer().authorService().find(1L)
        call.enqueue(object : Callback<Author> {
            override fun onResponse(call: Call<Author>?, response: Response<Author>?) {
                var author = response?.body()
                Log.i("author recebido", author.toString())
            }

            override fun onFailure(call: Call<Author>?, t: Throwable?) {
                Log.e("falhou", t?.message)
            }
        })
    }
}
