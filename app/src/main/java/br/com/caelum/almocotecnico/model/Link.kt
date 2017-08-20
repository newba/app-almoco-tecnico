package br.com.caelum.almocotecnico.model

/**
 * Created by alex on 09/08/17.
 */
data class Link(var rel: String = "",
                var href: String = "")

data class BookLink(val self: Link = Link(),
                    val authors: Link = Link())

data class AuthorLink(val self: Link = Link())