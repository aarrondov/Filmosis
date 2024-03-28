package com.example.filmosis.dataclass

import com.google.gson.annotations.SerializedName


data class PeliculaDetalles(
    @SerializedName("title") val titulo: String,
    @SerializedName("overview") val descripcion: String,
    @SerializedName("release_date") val fechaLanzamiento: String, @SerializedName("original_title") val tituloOriginal: String,
    @SerializedName("runtime") val duracion: Int,
    @SerializedName("popularity") val popularidad: Double,
    @SerializedName("vote_average") val calificacionPromedio: Double,
    @SerializedName("genres") val generos: List<Genero>,
    @SerializedName("production_companies") val companiasProduccion: List<CompaniaProduccion>

)


data class Genero(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val nombre: String
)

data class CompaniaProduccion(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val nombre: String,
    @SerializedName("origin_country") val paisOrigen: String
)

data class Creditos(
    @SerializedName("cast") val actores: List<Actor>,
)

data class Actor(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val nombre: String,
    @SerializedName("character") val personaje: String,
    @SerializedName("profile_path") val rutaPerfil: String?

)