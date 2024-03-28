package com.example.filmosis.data.model.tmdb

data class PersonsPage(
    val page: Int,
    val results: List<Person>,
    val total_pages: Int,
    val total_results: Int
)