package com.example.filmosis.network.interfaces

import com.example.filmosis.data.model.tmdb.Cast
import com.example.filmosis.data.model.tmdb.CastResponse
import com.example.filmosis.data.model.tmdb.CombinedCredits
import com.example.filmosis.data.model.tmdb.CreditsCast
import com.example.filmosis.data.model.tmdb.CreditsResponse
import com.example.filmosis.data.model.tmdb.Movie
import com.example.filmosis.data.model.tmdb.Moviefr
import com.example.filmosis.data.model.tmdb.MoviesPage
import com.example.filmosis.data.model.tmdb.PersonDetails
import com.example.filmosis.data.model.tmdb.PersonsPage
import com.example.filmosis.dataclass.MovieDetailsResponse
import com.example.filmosis.dataclass.Network
import com.example.filmosis.dataclass.NetworkDetailsResponse
import com.example.filmosis.dataclass.PeliculaDetalles


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiInterface {
    // Para montar querys personalizadas:
    @GET("discover/movie?language=es-ES&sort_by=popularity.desc")
    fun listPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Call<MoviesPage>

    @GET("discover/movie?language=en-US&sort_by=popularity.desc")
    fun listPopularMoviesEn(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Call<MoviesPage>

    // https://developer.themoviedb.org/reference/discover-movie

    @GET("discover/movie?language=es-ES&sort_by=popularity.desc")
    fun listUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("primary_release_date.gte") releaseDateGTE: String
    ): Call<MoviesPage>

    @GET("discover/movie?language=en-US&sort_by=popularity.desc")
    fun listUpcomingMoviesEn(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("primary_release_date.gte") releaseDateGTE: String
    ): Call<MoviesPage>

    @GET("movie/{movie_id}/recommendations?language=es-ES")
    fun getRecommendedMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MoviesPage>

    @GET("discover/movie?language=es-ES&sort_by=popularity.desc")
    fun listPopularMoviesWithGenres(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("with_genres") genres: String
    ): Call<MoviesPage>

    @GET("search/movie?language=es-ES&sort_by=popularity.desc")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("query") query: String
    ): Call<MoviesPage>


    @GET("movie/{movie_id}?language=es-ES")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("append_to_response") appendToResponse: String = "videos"
    ): Call<MovieDetailsResponse>

    @GET("movie/{movie_id}?language=en-US")
    fun getMovieDetailsEn(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("append_to_response") appendToResponse: String = "videos"
    ): Call<MovieDetailsResponse>

    @GET("discover/movie?language=es-ES&sort_by=vote_average.desc&vote_count.gte=50")
    fun listBestRatedMoviesWithGenres(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("with_genres") genres: String
    ): Call<MoviesPage>

    @GET("discover/movie?include_adult=false&include_video=false&language=es-ES&page=1&sort_by=vote_average.desc&without_genres=99,10755&vote_count.gte=200")
    fun listBestRatedMoves(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Call<MoviesPage>

    @GET("discover/movie?language=es-ES&sort_by=release_date.desc&vote_count.gte=20")
    fun listLatestMoviesWithGenres(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("with_genres") genres: String,
        @Query("primary_release_date.lte") releaseDateLTE: String // Fecha de lanzamiento menor o igual que
    ): Call<MoviesPage>

    @GET("discover/movie?language=es-ES&sort_by=release_date.desc&vote_count.gte=20")
    fun listLatestMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("primary_release_date.lte") releaseDateLTE: String // Fecha de lanzamiento menor o igual que
    ): Call<MoviesPage>

    @GET("trending/movie/week?language=es-ES")
    fun listTrendingMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Call<MoviesPage>

    @GET("discover/movie?language=es-ES&sort_by=popularity.desc")
    fun listUpcomingMoviesWithGenres(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("with_genres") genres: String,
        @Query("primary_release_date.gte") releaseDateGTE: String // Fecha de lanzamiento mayor o igual que
    ): Call<MoviesPage>

    @GET("discover/movie?language=en-US&sort_by=popularity.desc")
    fun listUpcomingMoviesWithGenresEn(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("with_genres") genres: String,
        @Query("primary_release_date.gte") releaseDateGTE: String // Fecha de lanzamiento mayor o igual que
    ): Call<MoviesPage>


    @GET("movie/{movie_id}/credits?language=es-ES")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<CreditsResponse>

    @GET("movie/{movie_id}/credits?language=es-ES")
    fun getMovieCredits2(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<CreditsCast>

    @GET("movie/{movie_id}/credits?language=en-US")
    fun getMovieCredits2En(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<CreditsCast>

    @GET("person/{person_id}/combined_credits?language=es-ES")
    fun getPersonCombinedCredits(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ) : Call<CombinedCredits>

    @GET("person/{person_id}/combined_credits?language=en-US")
    fun getPersonCombinedCreditsEn(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ) : Call<CombinedCredits>

    @GET("search/person?language=es-ES&page=1")
    fun searchPerson(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("query") query: String
    ): Call<PersonsPage>

    @GET("person/{person_id}?language=es-ES")
    fun getPersonDetails(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Call<PersonDetails>

    @GET("person/{person_id}?language=en-US")
    fun getPersonDetailsEn(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Call<PersonDetails>


    //TODO cambiar el netork con https://api.themoviedb.org/3/movie/{movie_id}/watch/providers
    //https://developer.themoviedb.org/reference/movie-watch-providers
    @GET("network/{id}")
    fun getNetworkDetails(
        @Path("id") networkId: Int,
        @Query("api_key") apiKey: String
    ): Call<Network>

    @GET("movie/{movie_id}/watch/providers")
    fun getStreamingProviders(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<NetworkDetailsResponse>


    @GET("movie/{movie_id}?language=es-ES")
    fun getMovieDetailsRecuperarGenres(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<Moviefr>

    @GET("movie/{movie_id}?language=en-US")
    fun getMovieDetailsRecuperarGenresEn(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<Moviefr>

    @GET("movie/{movie_id}?language=es-ES")
    fun getMovieDetailsRecuperar(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<Movie>

    @GET("movie/{movie_id}?language=en-US")
    fun getMovieDetailsRecuperarEn(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<Movie>
}


