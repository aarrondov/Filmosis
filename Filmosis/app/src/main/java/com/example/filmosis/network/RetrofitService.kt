package com.example.filmosis.network

import com.example.filmosis.config.DatosConexion
import com.example.filmosis.network.interfaces.TmdbApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private val tmdbRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(DatosConexion.TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val tmdbApi: TmdbApiInterface by lazy {
        tmdbRetrofit.create(TmdbApiInterface::class.java)
    }
}
