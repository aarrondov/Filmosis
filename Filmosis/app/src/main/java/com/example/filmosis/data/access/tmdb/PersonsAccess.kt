package com.example.filmosis.data.access.tmdb

import android.util.Log
import com.example.filmosis.config.DatosConexion
import com.example.filmosis.data.model.tmdb.Cast
import com.example.filmosis.data.model.tmdb.CombinedCredits
import com.example.filmosis.data.model.tmdb.Person
import com.example.filmosis.data.model.tmdb.PersonsPage
import com.example.filmosis.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonsAccess {
    fun getPersonCombinedCredits(personId: Int, callback: (List<Cast>) -> Unit) {
        val call = RetrofitService.tmdbApi.getPersonCombinedCredits(DatosConexion.API_KEY, DatosConexion.REGION, personId)

        call.enqueue(object : Callback<CombinedCredits> {
            override fun onFailure(call: Call<CombinedCredits>, t: Throwable) {
                Log.d("PersonsAccess", "getPersonCombinedCredits onFailure: " + t.message)
            }

            override fun onResponse(call: Call<CombinedCredits>, response: Response<CombinedCredits>) {
                val casts = response.body()?.cast

                if (casts != null) {
                    callback.invoke(casts)
                }
            }
        })
    }

    fun searchPerson(query: String, callback: (List<Person>) -> Unit) {
        val call = RetrofitService.tmdbApi.searchPerson(DatosConexion.API_KEY, DatosConexion.REGION, query)

        call.enqueue(object : Callback<PersonsPage> {
            override fun onFailure(call: Call<PersonsPage>, t: Throwable) {
                Log.d("PersonsAccess", "getPersonCombinedCredits onFailure: " + t.message)
            }

            override fun onResponse(call: Call<PersonsPage>, response: Response<PersonsPage>) {
                val people = response.body()?.results

                if (people != null) {
                    callback.invoke(people)
                }
            }
        })
    }
}