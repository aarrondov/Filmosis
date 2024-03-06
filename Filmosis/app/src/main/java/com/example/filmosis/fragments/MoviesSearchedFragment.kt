package com.example.filmosis.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmosis.R
import com.example.filmosis.adapters.ListedMoviesAdapter
import com.example.filmosis.data.access.tmdb.MoviesAccess
import com.example.filmosis.data.model.tmdb.Movie

class MoviesSearchedFragment : Fragment() {
    private val moviesAccess = MoviesAccess()
    private lateinit var rv: RecyclerView

    private var moviesList: ArrayList<Movie> = ArrayList()

    companion object {
        private const val ARG_QUERY = "tmdbQuery"

        fun newInstance(query: String): MoviesSearchedFragment {
            val fragment = MoviesSearchedFragment()
            val args = Bundle()

            args.putString(ARG_QUERY, query)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_searched, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
    }

    private fun setup(view: View) {
        val query = arguments?.getString(ARG_QUERY)
        val searchView: SearchView = view.findViewById(R.id.moviesSearched_searchView)
        searchView.setQuery(query, false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    performSearch(query)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        rv = view.findViewById(R.id.moviesSearched_recyclerView)
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        if (query != null) {
            addSearchedMoviesToRv(query)
        }
    }

    private fun performSearch(query: String) {
        addSearchedMoviesToRv(query)
    }

    private fun addSearchedMoviesToRv(query: String) {
        moviesAccess.searchMovie(query) { result ->
            moviesList.clear()

            result.forEach { movie ->
                moviesList.add(movie)
            }

            if (moviesList.isEmpty()) {
                Toast.makeText(requireContext(), "No hay resultados", Toast.LENGTH_LONG).show()
            } else {
                val moviesAdapter = ListedMoviesAdapter(moviesList) { movieClicked ->

                }

                rv.adapter = moviesAdapter
            }
        }
    }

    private fun finishFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }
}