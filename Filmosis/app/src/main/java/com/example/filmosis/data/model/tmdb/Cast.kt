package com.example.filmosis.data.model.tmdb

data class Cast(
    val adult: Boolean,
    val backdrop_path: String,
    val character: String,
    val credit_id: String,
    val episode_count: Int,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val media_type: String,
    val name: String,
    val order: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val known_for_department: String,
    val profile_path : String
)

data class CastResponse(
    val crew: List<Cast>,

    )