package com.example.filmosis.utilities.tmdb

object TmdbData {
    // Datos recogidos de la API de TMDB para usarlos sin tener que realizar la query

    // Actualizado: 25 Febrero 2024
    // genre/movie/list?language=es'
    val movieGenresIds: List<Pair<Int, String>> = mapOf(
        28 to "Acción",
        12 to "Aventura",
        16 to "Animación",
        35 to "Comedia",
        80 to "Crimen",
        99 to "Documental",
        18 to "Drama",
        10751 to "Familia",
        14 to "Fantasía",
        36 to "Historia",
        27 to "Terror",
        10402 to "Música",
        9648 to "Misterio",
        10749 to "Romance",
        878 to "Ciencia ficción",
        10770 to "Película de TV",
        53 to "Suspense",
        10752 to "Bélica",
        37 to "Western"
    ).toList().sortedBy { it.second }

    // Actualizado: 25 Febrero 2024
    // genre/movie/list?language=en'
    val movieGenresIdsEn: List<Pair<Int, String>> = mapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western"
    ).toList().sortedBy { it.second }
}