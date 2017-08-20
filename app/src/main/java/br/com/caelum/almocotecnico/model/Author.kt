package br.com.caelum.almocotecnico.model

import br.com.caelum.almocotecnico.representation.AuthorRepresentation
import com.fasterxml.jackson.annotation.JsonIgnore

/**
 * Created by alex on 07/08/17.
 */
data class Author(var name: String = "",
                  @JsonIgnore val representation: AuthorRepresentation = AuthorRepresentation())