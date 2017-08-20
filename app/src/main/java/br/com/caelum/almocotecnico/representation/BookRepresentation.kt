package br.com.caelum.almocotecnico.representation

import br.com.caelum.almocotecnico.model.Book
import br.com.caelum.almocotecnico.model.Link
import br.com.caelum.almocotecnico.model.BookLink
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by alex on 19/08/17.
 */


data class BookRepresentation(val title: String,
                              val summary: String,
                              var inserted: BookRepresentationInserted = BookRepresentationInserted(),
                              var active: BookRepresentationActive = BookRepresentationActive()) {

    val book by lazy { Book(title = title, summary = summary) }

    fun authors(): String {
        return try {
            active.links.first { it.rel.equals("authors") }.href
        } catch (e: NoSuchElementException) {
            inserted.links.authors.href
        }
    }

    fun self(): String {
        return try {
            active.links.first { it.rel.equals("self") }.href
        } catch (e: NoSuchElementException) {
            inserted.links.self.href
        }
    }

}

data class BookRepresentationInserted(val title: String = "",
                                      val summary: String = "",
                                      @JsonProperty("_links")
                                      val links: BookLink = BookLink()) {
    val book by lazy { Book(title = title, summary = summary) }
}


data class BookRepresentationActive(val title: String = "",
                                    val summary: String = "",
                                    val links: List<Link> = listOf()) {

    val book by lazy { Book(title = title, summary = summary) }
}