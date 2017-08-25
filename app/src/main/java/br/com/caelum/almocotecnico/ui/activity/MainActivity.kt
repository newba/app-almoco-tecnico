package br.com.caelum.almocotecnico.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.caelum.almocotecnico.R
import br.com.caelum.almocotecnico.ui.fragment.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTabs()
    }

    private fun setupTabs() {
        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager)
        main_viewpager.adapter = mainPagerAdapter
        main_sliding_tabs.setupWithViewPager(main_viewpager)
    }
}
