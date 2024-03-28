package com.example.filmosis.data.model.tmdb

data class CombinedCredits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)