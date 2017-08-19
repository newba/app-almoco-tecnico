package br.com.caelum.almocotecnico.representation

import br.com.caelum.almocotecnico.model.Book
import br.com.caelum.almocotecnico.model.Link
import br.com.caelum.almocotecnico.model.ReturnLink
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by alex on 19/08/17.
 */


data class BookRepresentation(val title: String,
                              val summary: String,
                              var inserted: BookRepresentationInserted = BookRepresentationInserted(),
                              var active: BookRepresentationActive = BookRepresentationActive()) {

    val book by lazy { Book(title = title, summary = summary) }

    fun insertedSelf(): String {
        return inserted.links.self.href
    }

    fun insertedAuthors(): String {
        return inserted.links.authors.href
    }

    fun activeSelf(): String {
        return active.links.first { it.rel.equals("self") }.href
    }

    fun activeAuthors(): String {
        return active.links.first { it.rel.equals("authors") }.href
    }
}

data class BookRepresentationInserted(val title: String = "",
                                      val summary: String = "",
                                      @JsonProperty("_links")
                                      val links: ReturnLink = ReturnLink()) {
    val book by lazy { Book(title = title, summary = summary) }
}


data class BookRepresentationActive(val title: String = "",
                                    val summary: String = "",
                                    val links: List<Link> = listOf()) {

    val book by lazy { Book(title = title, summary = summary) }
}