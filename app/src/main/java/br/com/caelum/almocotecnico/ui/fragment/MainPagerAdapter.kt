package br.com.caelum.almocotecnico.ui.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by alex on 08/08/17.
 */
class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val authorsTitle = "Authors"
    private val booksTitle = "Books"
    private val tabTitles = listOf(authorsTitle, booksTitle)

    override fun getItem(position: Int): Fragment {
        return when {
            position == 0 -> AuthorListFragment()
            position == 1 -> BooksListFragment()
            else -> {
                throw IllegalArgumentException("Id não existente")
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> authorsTitle
            1 -> booksTitle
            else -> super.getPageTitle(position)
        }
    }

    override fun getCount(): Int {
        return tabTitles.size
    }
}