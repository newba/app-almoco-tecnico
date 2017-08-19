package br.com.caelum.almocotecnico.model;

import br.com.caelum.almocotecnico.representation.BookRepresentation
import com.fasterxml.jackson.annotation.JsonIgnore

data class Book(var title: String = "",
                var summary: String = "",
                @JsonIgnore var authors: List<Author> = arrayListOf(),
                @JsonIgnore val representation: BookRepresentation = BookRepresentation(title = title, summary = summary))