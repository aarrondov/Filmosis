package com.example.filmosis.dataclass

data class ListedMovie(
    val title: String,
    val poster_path: String,
    val releaseDate: String,
    val averageVote: Double,
    val id: Int
)
