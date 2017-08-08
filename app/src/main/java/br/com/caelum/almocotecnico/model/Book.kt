package br.com.caelum.almocotecnico.model;

class Book(var id: Long = 0,
           var title: String = "",
           var summary: String = "",
           var authors: List<Author> = arrayListOf())