package com.example.filmosis.data.model.tmdb

data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val spoken_languages : List<Idioma>
)

//https://developer.themoviedb.org/reference/movie-details
data class Moviefr(
    val adult: Boolean,
    val backdrop_path: String,
    val genres: List<Genre>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val spoken_languages : List<Idioma>
)

data class Idioma(val english_name:String, val iso_639_1 : String, val name : String )

data class Genre(
    val id: Int,
    val name: String
)






