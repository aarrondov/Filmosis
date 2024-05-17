package com.example.filmosis.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmosis.R
import com.example.filmosis.adapters.ListedMoviesAdapter
import com.example.filmosis.data.access.tmdb.MoviesAccess
import com.example.filmosis.data.model.tmdb.Movie
import com.example.filmosis.init.FirebaseInitializer


class MoviesSearchedFragment : Fragment() {

    private val moviesAccess = MoviesAccess()
    private lateinit var rv: RecyclerView
    private var moviesList: ArrayList<Movie> = ArrayList()

    companion object {
        private const val ARG_QUERY = "tmdbQuery"
        private const val ARG_ADD_TO_LIST = "performAddToList"
        private const val ARG_LIST_ID = "listId"
        private const val ARG_FRAGMENT_PREV = ""

        fun newInstance(query: String, performAddToList: Boolean = false, listId: String = "",fragmentPrev: String = ""): MoviesSearchedFragment {
            val fragment = MoviesSearchedFragment()
            val args = Bundle()

            args.putString(ARG_QUERY, query)
            args.putBoolean(ARG_ADD_TO_LIST, performAddToList)
            args.putString(ARG_LIST_ID, listId)
            args.putString(ARG_FRAGMENT_PREV,fragmentPrev)

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

        if (query.isNullOrEmpty()) {
            loadPopularMovies()
        } else {
            addSearchedMoviesToRv(query)
        }
    }

    private fun performSearch(query: String) {
        addSearchedMoviesToRv(query)
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun loadPopularMovies() {
        moviesAccess.listPopularMovies { movies ->
            moviesList.addAll(movies)
            rv.adapter = ListedMoviesAdapter(moviesList) { movie ->
                handleMovieClick(movie)
            }
        }
    }

    private fun addSearchedMoviesToRv(query: String) {
        moviesAccess.searchMovie(query) { result ->
            moviesList.clear()
            moviesList.addAll(result)

            if (moviesList.isEmpty()) {
                showToast("No hay resultados")
            } else {
                rv.adapter = ListedMoviesAdapter(moviesList) { movie ->
                    handleMovieClick(movie)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun handleMovieClick(movie: Movie) {
        if (arguments?.getBoolean(ARG_ADD_TO_LIST) == true) {
            showAddToListDialog(movie)
        } else {
            navigateToMovieDetailFragment(movie.id)
        }
    }

    private fun showAddToListDialog(movie: Movie) {
        val listId: String? = arguments?.getString(ARG_LIST_ID)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmación")
        builder.setMessage("¿Añadir ${movie.title} a la lista?")
            .setPositiveButton("Aceptar") { dialog, id ->
                Log.d("MovieInListFragment","Añadiendo linea 143")
                Log.d("MovieInListFragment arg: ",arguments?.getString(ARG_FRAGMENT_PREV).toString())
                if (arguments?.getString(ARG_FRAGMENT_PREV) == "ListsFragment"){
                    listId?.let { addMovieToList(movie, it) }
                }else if(arguments?.getString(ARG_FRAGMENT_PREV) == "ExpertListsFragment"){
                    listId?.let { addMovieToListExpert(movie, it)}
                }else {
                    listId?.let { addMovieToListPopular(movie, it)}

                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun navigateToMovieDetailFragment(movieId: Int) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, PeliculaSeleccionadaFragment.newInstance(movieId))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun addMovieToList(movie: Movie, desiredListId: String) {
        val userEmail = FirebaseInitializer.authInstance.currentUser?.email.toString()
        val listsRef = FirebaseInitializer.firestoreInstance.collection("lists").document(userEmail)

        listsRef
            .get()
            .addOnSuccessListener { document ->
                val data = document.data

                data?.forEach { (key, value) ->
                    val listData = value as? Map<*, *>
                    val listId = listData?.get("listId").toString()

                    if (listId == desiredListId) {
                        val moviesList = document.get("lista_$listId.listMovies") as? MutableList<Map<String, Any>>

                        moviesList?.add(
                            mapOf(
                                "id" to movie.id,
                                "title" to movie.title,
                                "poster_path" to movie.poster_path,
                                "releaseDate" to movie.release_date,
                                "averageVote" to movie.vote_average
                            )
                        )

                        listsRef.update("lista_$listId.listMovies", moviesList)
                            .addOnSuccessListener {
                                requireActivity().onBackPressedDispatcher.onBackPressed()
                            }
                            .addOnFailureListener { exception ->
                                handleAddMovieToListError(exception)
                            }
                    }
                }

            }
            .addOnFailureListener { exception ->
                Log.d("bruh", "Error fetching lists: $exception")
            }
    }

    private fun addMovieToListPopular(movie: Movie, desiredListId: String) {
        val listsRef = FirebaseInitializer.firestoreInstance.collection("lists").document("ListasPopulares")
        Log.d("MovieInListFragment","Añadiendo peli a populares")

        listsRef
            .get()
            .addOnSuccessListener { document ->
                val data = document.data

                data?.forEach { (key, value) ->
                    val listData = value as? Map<*, *>
                    val listId = listData?.get("listId").toString()

                    if (listId == desiredListId) {
                        val moviesList = document.get("lista_$listId.listMovies") as? MutableList<Map<String, Any>>

                        moviesList?.add(
                            mapOf(
                                "id" to movie.id,
                                "title" to movie.title,
                                "poster_path" to movie.poster_path,
                                "releaseDate" to movie.release_date,
                                "averageVote" to movie.vote_average
                            )
                        )

                        listsRef.update("lista_$listId.listMovies", moviesList)
                            .addOnSuccessListener {
                                requireActivity().onBackPressedDispatcher.onBackPressed()
                            }
                            .addOnFailureListener { exception ->
                                handleAddMovieToListError(exception)
                            }
                    }
                }

            }
            .addOnFailureListener { exception ->
                Log.d("bruh", "Error fetching lists: $exception")
            }
    }

    private fun addMovieToListExpert(movie: Movie, desiredListId: String) {
        val listsRef = FirebaseInitializer.firestoreInstance.collection("lists").document("ListasExpertos")
        Log.d("MovieInListFragment","Añadiendo peli a populares")

        listsRef
            .get()
            .addOnSuccessListener { document ->
                val data = document.data

                data?.forEach { (key, value) ->
                    val listData = value as? Map<*, *>
                    val listId = listData?.get("listId").toString()

                    if (listId == desiredListId) {
                        val moviesList = document.get("lista_$listId.listMovies") as? MutableList<Map<String, Any>>

                        moviesList?.add(
                            mapOf(
                                "id" to movie.id,
                                "title" to movie.title,
                                "poster_path" to movie.poster_path,
                                "releaseDate" to movie.release_date,
                                "averageVote" to movie.vote_average
                            )
                        )

                        listsRef.update("lista_$listId.listMovies", moviesList)
                            .addOnSuccessListener {
                                requireActivity().onBackPressedDispatcher.onBackPressed()
                            }
                            .addOnFailureListener { exception ->
                                handleAddMovieToListError(exception)
                            }
                    }
                }

            }
            .addOnFailureListener { exception ->
                Log.d("bruh", "Error fetching lists: $exception")
            }
    }


    private fun handleAddMovieToListError(exception: Exception) {
        requireActivity().onBackPressedDispatcher.onBackPressed()
        Toast.makeText(requireContext(), "Error al añadir la película a la lista: ${exception.message}", Toast.LENGTH_SHORT).show()
    }
}
