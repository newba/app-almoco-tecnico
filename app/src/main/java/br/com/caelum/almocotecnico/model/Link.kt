package br.com.caelum.almocotecnico.model

/**
 * Created by alex on 09/08/17.
 */
data class Link(var rel: String = "",
                var href: String = "")

data class ReturnLink(var self: Link = Link(),
                      var authors: Link = Link())