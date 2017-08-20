package br.com.caelum.almocotecnico.representation

import br.com.caelum.almocotecnico.model.Author
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by alex on 20/08/17.
 */


data class AuthorRepresentation(val name: String = "",
                                var active: AuthorRepresentationActive = AuthorRepresentationActive(),
                                var inserted: AuthorRepresentationInserted = AuthorRepresentationInserted()) {
    val author by lazy { Author(name = name) }

    fun self(): String {
        return try {
            active.links.first { it.rel.equals("self") }.href
        } catch (e: NoSuchElementException) {
            inserted.links.self.href
        }
    }
}

data class AuthorRepresentationActive(val name: String = "",
                                      val links: List<Link> = listOf()) {
    val author by lazy { Author(name = name) }
}

data class AuthorRepresentationInserted(val name: String = "",
                                        @JsonProperty("_links")
                                        val links: AuthorLink = AuthorLink()) {
    val author by lazy { Author(name = name) }
}