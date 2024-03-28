package com.example.filmosis.data.model.tmdb

data class CreditsCast(
    val cast: List<CastX>,
    val crew: List<CrewX>,
    val id: Int
)