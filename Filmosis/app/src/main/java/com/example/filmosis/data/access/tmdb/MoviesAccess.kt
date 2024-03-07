package com.example.filmosis.data.access.tmdb

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.filmosis.adapters.ServicioAdapter
import com.example.filmosis.config.DatosConexion
import com.example.filmosis.data.model.tmdb.Cast
import com.example.filmosis.data.model.tmdb.CastResponse
import com.example.filmosis.data.model.tmdb.CreditsResponse
import com.example.filmosis.data.model.tmdb.Crew

import com.example.filmosis.data.model.tmdb.MoviesPage
import com.example.filmosis.data.model.tmdb.Movie
import com.example.filmosis.data.model.tmdb.Person
import com.example.filmosis.dataclass.MovieDetailsResponse
import com.example.filmosis.dataclass.NetworkDetailsResponse
import com.example.filmosis.dataclass.Servicio
import com.example.filmosis.network.RetrofitService
import com.example.filmosis.network.interfaces.TmdbApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MoviesAccess {

    fun listPopularMovies(callback: (List<Movie>) -> Unit) {
        val call =
            RetrofitService.tmdbApi.listPopularMovies(DatosConexion.API_KEY, DatosConexion.REGION)

        call.enqueue(object : Callback<MoviesPage> {
            override fun onFailure(call: Call<MoviesPage>, t: Throwable) {
                Log.d("MoviesAccess", "listPopularMovies onFailure: " + t.message)
            }

            override fun onResponse(call: Call<MoviesPage>, response: Response<MoviesPage>) {
                val movies = response.body()?.results

                if (movies != null) {
                    callback.invoke(movies)
                }
            }
        })
    }

    fun listPopularMoviesWithGenres(genres: List<Int>, callback: (List<Movie>) -> Unit) {
        val call = RetrofitService.tmdbApi.listPopularMoviesWithGenres(
            DatosConexion.API_KEY,
            DatosConexion.REGION,
            genres.joinToString(separator = "||")
        )

        call.enqueue(object : Callback<MoviesPage> {
            override fun onFailure(call: Call<MoviesPage>, t: Throwable) {
                Log.d("MoviesAccess", "listPopularMoviesWithGenres onFailure: " + t.message)
            }

            override fun onResponse(call: Call<MoviesPage>, response: Response<MoviesPage>) {
                val movies = response.body()?.results

                if (movies != null) {
                    callback.invoke(movies)
                }
            }
        })
    }

    fun listUpcomingMovies(callback: (List<Movie>) -> Unit) {
        val currentDate = getCurrentDate()
        val call = RetrofitService.tmdbApi.listUpcomingMovies(
            DatosConexion.API_KEY,
            DatosConexion.REGION,
            currentDate
        )
        call.enqueue(object : Callback<MoviesPage> {
            override fun onFailure(call: Call<MoviesPage>, t: Throwable) {
                Log.d("MoviesAccess", "listUpcomingMovies onFailure: " + t.message)
            }

            override fun onResponse(call: Call<MoviesPage>, response: Response<MoviesPage>) {
                val movies = response.body()?.results

                if (movies != null) {
                    callback.invoke(movies)
                }
            }
        })
    }

    fun listRecommendedMovies(movieId: Int, callback: (List<Movie>) -> Unit) {
        val call = RetrofitService.tmdbApi.getRecommendedMovies(movieId, DatosConexion.API_KEY)

        call.enqueue(object : Callback<MoviesPage> {
            override fun onFailure(call: Call<MoviesPage>, t: Throwable) {
                Log.d("MoviesAccess", "listRecommendedMovies onFailure: " + t.message)
            }

            override fun onResponse(call: Call<MoviesPage>, response: Response<MoviesPage>) {
                val movies = response.body()?.results

                if (movies != null) {
                    callback.invoke(movies)
                }
            }
        })
    }

    fun searchMovie(query: String, callback: (List<Movie>) -> Unit) {
        val call =
            RetrofitService.tmdbApi.searchMovie(DatosConexion.API_KEY, DatosConexion.REGION, query)

        call.enqueue(object : Callback<MoviesPage> {
            override fun onFailure(call: Call<MoviesPage>, t: Throwable) {
                Log.d("MoviesAccess", "searchMovie onFailure: " + t.message)
            }

            override fun onResponse(call: Call<MoviesPage>, response: Response<MoviesPage>) {
                val movies = response.body()?.results

                if (movies != null) {
                    callback.invoke(movies)
                }
            }
        })
    }

    //Para conseguir el link del video
    fun getMovieDetails(movieId: Int, callback: (String?) -> Unit) {
        val call = RetrofitService.tmdbApi.getMovieDetails(movieId, DatosConexion.API_KEY, "videos")

        call.enqueue(object : Callback<MovieDetailsResponse> {
            override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                Log.d("MoviesAccess", "getMovieDetails onFailure: " + t.message)
                callback.invoke(null)
            }

            override fun onResponse(
                call: Call<MovieDetailsResponse>,
                response: Response<MovieDetailsResponse>
            ) {
                val videoKey = response.body()?.videos?.results?.firstOrNull()?.key
                if (videoKey != null) {
                    val videoUrl = "https://www.youtube.com/embed/$videoKey"
                    callback.invoke(videoUrl)
                } else {
                    callback.invoke(null)
                }
            }
        })
    }


    fun listTrendingMovies(callback: (List<Movie>) -> Unit) {
        val call =
            RetrofitService.tmdbApi.listTrendingMovies(DatosConexion.API_KEY, DatosConexion.REGION)

        call.enqueue(object : Callback<MoviesPage> {
            override fun onFailure(call: Call<MoviesPage>, t: Throwable) {
                Log.d("MoviesAccess", "listTrendingMovies onFailure: " + t.message)
            }

            override fun onResponse(call: Call<MoviesPage>, response: Response<MoviesPage>) {
                val movies = response.body()?.results

                if (movies != null) {
                    callback.invoke(movies)
                }
            }
        })
    }

    fun listBestRatedMoviesWithGenres(genres: List<Int>, callback: (List<Movie>) -> Unit) {
        val call = RetrofitService.tmdbApi.listBestRatedMoviesWithGenres(
            DatosConexion.API_KEY,
            DatosConexion.REGION,
            genres.joinToString(separator = "||")
        )

        call.enqueue(object : Callback<MoviesPage> {
            override fun onFailure(call: Call<MoviesPage>, t: Throwable) {
                Log.d("MoviesAccess", "listBestRatedMoviesWithGenres onFailure: " + t.message)
            }

            override fun onResponse(call: Call<MoviesPage>, response: Response<MoviesPage>) {
                val movies = response.body()?.results

                if (movies != null) {
                    callback.invoke(movies)
                }
            }
        })
    }

    fun listLatestMoviesWithGenres(genres: List<Int>, callback: (List<Movie>) -> Unit) {
        val call = RetrofitService.tmdbApi.listLatestMoviesWithGenres(
            DatosConexion.API_KEY,
            DatosConexion.REGION,
            genres.joinToString(separator = "||"),
            getCurrentDate()
        )

        call.enqueue(object : Callback<MoviesPage> {
            override fun onFailure(call: Call<MoviesPage>, t: Throwable) {
                Log.d("MoviesAccess", "listLatestMoviesWithGenres onFailure: " + t.message)
            }

            override fun onResponse(call: Call<MoviesPage>, response: Response<MoviesPage>) {
                val movies = response.body()?.results

                if (movies != null) {
                    callback.invoke(movies)
                }
            }
        })
    }




    fun listUpcomingMoviesWithGenres(genres: List<Int>, callback: (List<Movie>) -> Unit) {
        val call = RetrofitService.tmdbApi.listUpcomingMoviesWithGenres(
            DatosConexion.API_KEY,
            DatosConexion.REGION,
            genres.joinToString(separator = "||"),
            getCurrentDate()
        )

        call.enqueue(object : Callback<MoviesPage> {
            override fun onFailure(call: Call<MoviesPage>, t: Throwable) {
                Log.d("MoviesAccess", "listUpcomingMoviesWithGenres onFailure: " + t.message)
            }

            override fun onResponse(call: Call<MoviesPage>, response: Response<MoviesPage>) {
                val movies = response.body()?.results

                if (movies != null) {
                    callback.invoke(movies)
                }
            }
        })
    }

    //TODO aqui esta el problema
    fun getMovieData(movieId: Int, callback: (Movie?) -> Unit) {
        val call = RetrofitService.tmdbApi.getMovieDetailsRecuperar(movieId, DatosConexion.API_KEY)

        call.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("MoviesAccess", "getMovieData onFailure: " + t.message)
                callback.invoke(null)
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                val movie = response.body()
                callback.invoke(movie)
            }
        })
    }





    fun getDirectorDetails(movieId: Int, callback: (List<Crew>?) -> Unit) {
        val call = RetrofitService.tmdbApi.getMovieCredits(movieId, DatosConexion.API_KEY)

        call.enqueue(object : Callback<CreditsResponse> {
            override fun onResponse(
                call: Call<CreditsResponse>,
                response: Response<CreditsResponse>
            ) {
                if (response.isSuccessful) {
                    val creditsResponse = response.body()
                    if (creditsResponse != null) {
                        val directorsList = creditsResponse.crew.filter { it.job == "Director" }
                        callback(directorsList)
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<CreditsResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getActorDetails(movieId: Int, callback: (List<Cast>?) -> Unit) {
        val call = RetrofitService.tmdbApi.getMovieCredits2(movieId, DatosConexion.API_KEY)
        call.enqueue(object : Callback<CastResponse> {
            override fun onResponse(
                call: Call<CastResponse>,
                response: Response<CastResponse>
            ) {
                if (response.isSuccessful) {
                    val creditsResponse = response.body()
                    if (creditsResponse != null) {
                        val actorList = creditsResponse.crew.filter { it.known_for_department == "Acting" }
                        callback(actorList)
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<CastResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
    //TODO no me lo borreis
//    fun fetchNetworkDetails(networkId: Int, callback: (NetworkDetailsResponse?) -> Unit) {
//        val call = RetrofitService.tmdbApi.getNetworkDetails(networkId, DatosConexion.API_KEY)
//
//        call.enqueue(object : Callback<NetworkDetailsResponse> {
//            override fun onResponse(
//                call: Call<NetworkDetailsResponse>,
//                response: Response<NetworkDetailsResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val networkDetailsResponse = response.body()
//                    callback(networkDetailsResponse)
//                } else {
//                    println("Solicitud fallida - Código de estado: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<NetworkDetailsResponse>, t: Throwable) {
//                println("Error de red: ${t.message}")
//            }
//        })
//    }

    fun fetchNetworkDetails2(movieId: Int, callback: (NetworkDetailsResponse?) -> Unit) {
        val call = RetrofitService.tmdbApi.getStreamingProviders(movieId, DatosConexion.API_KEY)

        call.enqueue(object : Callback<NetworkDetailsResponse> {
            override fun onResponse(
                call: Call<NetworkDetailsResponse>,
                response: Response<NetworkDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    val networkDetailsResponse = response.body()
                    callback(networkDetailsResponse)
                } else {
                    println("Solicitud fallida - Código de estado: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<NetworkDetailsResponse>, t: Throwable) {
                println("Error de red: ${t.message}")
            }
        })
    }



    //Lo necesito para hacer la consulta a la bd
    private fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentDate.format(formatter)
    }

}