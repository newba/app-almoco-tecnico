package br.com.caelum.almocotecnico.representation

import br.com.caelum.almocotecnico.model.Book
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by alex on 19/08/17.
 */


data class BookRepresentation(val title: String,
                              val summary: String,
                              var inserted: BookRepresentationInserted = BookRepresentationInserted(),
                              var active: BookRepresentationActive = BookRepresentationActive()) {
    fun authors(): String {
        return try {
            active.links.first { it.rel == "authors" }.href
        } catch (e: NoSuchElementException) {
            inserted.links.authors.href
        }
    }

    fun self(): String {
        return try {
            active.links.first { it.rel == "self" }.href
        } catch (e: NoSuchElementException) {
            inserted.links.self.href
        }
    }

}

data class BookRepresentationInserted(private val title: String = "",
                                      private val summary: String = "",
                                      @JsonProperty("_links")
                                      val links: BookLink = BookLink()) {
    val book by lazy { Book(title = title, summary = summary) }
}


data class BookRepresentationActive(private val title: String = "",
                                    private val summary: String = "",
                                    val links: List<Link> = listOf()) {

    val book by lazy { Book(title = title, summary = summary) }
}