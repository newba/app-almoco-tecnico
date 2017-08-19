package br.com.caelum.almocotecnico.dao

import br.com.caelum.almocotecnico.model.Author

/**
 * Created by alex on 10/08/17.
 */
class AuthorDAO {

    companion object {
        private val authors: MutableList<Author> = mutableListOf<Author>()
    }

    fun add(author: Author) {
        authors.add(author)
    }

    fun all(): List<Author> {
        return authors.toList()
    }

    fun add(authors: List<Author>) {
        AuthorDAO.Companion.authors.addAll(authors)
    }
}