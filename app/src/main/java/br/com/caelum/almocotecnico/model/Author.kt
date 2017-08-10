package br.com.caelum.almocotecnico.model

/**
 * Created by alex on 07/08/17.
 */
data class Author(var id: Long = 0,
                  var name: String = "",
                  private var links: List<Link> = listOf()) {

    fun self(): String {
        return links.first().href
    }
}