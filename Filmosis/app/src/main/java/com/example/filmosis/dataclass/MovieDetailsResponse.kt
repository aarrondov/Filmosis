package com.example.filmosis.dataclass

import com.google.gson.annotations.SerializedName

//Mapeamos la respuesta que nos da la API
data class MovieDetailsResponse(
    @SerializedName("videos") val videos: Videos
)

data class Videos(
    @SerializedName("results") val results: List<Video>
)

data class Video(
    @SerializedName("key") val key: String
)