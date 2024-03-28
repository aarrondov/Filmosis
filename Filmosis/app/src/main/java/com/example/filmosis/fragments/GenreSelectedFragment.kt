package com.example.filmosis.fragments

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmosis.R
import com.example.filmosis.adapters.CarouselMoviesAdapter
import com.example.filmosis.adapters.ListedMoviesAdapter
import com.example.filmosis.data.access.tmdb.MoviesAccess
import com.example.filmosis.data.model.tmdb.Movie

class GenreSelectedFragment : Fragment() {

    private val moviesAccess = MoviesAccess()

    private var moviesListTrending: ArrayList<Movie> = ArrayList()
    private var filteredMoviesList: ArrayList<Movie> = ArrayList()

    private lateinit var genres: List<Int>

    private lateinit var popularMoviesRv: RecyclerView
    private lateinit var moviesFilteredRv: RecyclerView

    companion object {
        private const val ARG_GENRE_ID = "genreId"
        private const val ARG_GENRE_NAME = "genreName"

        fun newInstance(genreId: Int, genreName: String): GenreSelectedFragment {
            val fragment = GenreSelectedFragment()
            val args = Bundle()
            args.putInt(ARG_GENRE_ID, genreId)
            args.putString(ARG_GENRE_NAME, genreName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_genre_selected, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
    }

    private fun setup(view: View) {
        val genreId = arguments?.getInt(ARG_GENRE_ID) ?: -1
        val genreName = arguments?.getString(ARG_GENRE_NAME) ?: ""

        genres = listOf(genreId)

        view.findViewById<TextView>(R.id.genreSelected_genreTitleTextView).text = genreName

        popularMoviesRv = view.findViewById(R.id.genreSelected_popularMoviesRecyclerView)
        // No hace falta layoutManager porque es Carousel recycler view y ya viene integrado
        addTrendingMoviesWithGenreToRv(genres)

        moviesFilteredRv = view.findViewById(R.id.genreSelected_moviesFilteredRecyclerView)
        moviesFilteredRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        initMoviesFilteredRv()
        initFilterMoviesButtons(view)
    }

    private fun resolveThemeColor(context: Context, attr: Int): Int {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(attr, typedValue, true)
        return typedValue.data
    }

    private fun initFilterMoviesButtons(view: View) {
        val bestRatedBtn: Button = view.findViewById(R.id.genreSelected_topRatedBtn)
        val popularBtn: Button = view.findViewById(R.id.genreSelected_popularBtn)
        val latestBtn: Button = view.findViewById(R.id.genreSelected_latestBtn)
        val upcomingBtn: Button = view.findViewById(R.id.genreSelected_upcomingBtn)

        val buttons = listOf(bestRatedBtn, popularBtn, latestBtn, upcomingBtn)

        for (actualButton in buttons) {

            actualButton.setBackgroundColor(resolveThemeColor(requireContext(), androidx.appcompat.R.attr.colorBackgroundFloating))

            actualButton.setOnClickListener {
                if (!actualButton.isSelected) {
                    for (button in buttons) {
                        button.isSelected = false
                        button.setBackgroundColor(resolveThemeColor(requireContext(), androidx.appcompat.R.attr.colorBackgroundFloating))
                    }

                    actualButton.isSelected = true
                    actualButton.setBackgroundColor(resolveThemeColor(requireContext(), androidx.appcompat.R.attr.colorPrimary))

                    filteredMoviesList.clear()
                    when (actualButton.id) {
                        R.id.genreSelected_topRatedBtn -> moviesAccess.listBestRatedMoviesWithGenres(genres) { updateFilteredMoviesList(it) }
                        R.id.genreSelected_popularBtn -> moviesAccess.listPopularMoviesWithGenres(genres) { updateFilteredMoviesList(it) }
                        R.id.genreSelected_latestBtn -> moviesAccess.listLatestMoviesWithGenres(genres) { updateFilteredMoviesList(it) }
                        R.id.genreSelected_upcomingBtn -> moviesAccess.listUpcomingMoviesWithGenres(genres) { updateFilteredMoviesList(it) }
                    }
                }
            }
        }

        bestRatedBtn.isSelected = true
        bestRatedBtn.setBackgroundColor(resolveThemeColor(requireContext(), androidx.appcompat.R.attr.colorPrimary))
    }

    private fun updateFilteredMoviesList (movies: List<Movie>) {

        movies.forEach { movie ->
            filteredMoviesList.add(movie)

        }

        val moviesAdapter = ListedMoviesAdapter(filteredMoviesList) {
            movieClicked ->

            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, PeliculaSeleccionadaFragment.newInstance(movieClicked.id))
            transaction.addToBackStack(null)
            transaction.commit()
        }

        moviesFilteredRv.adapter = moviesAdapter
    }

    private fun addTrendingMoviesWithGenreToRv(genres: List<Int>) {
        moviesAccess.listPopularMoviesWithGenres(genres) { result ->
            result.forEach { movie ->
                moviesListTrending.add(movie)
            }

            val moviesAdapter = CarouselMoviesAdapter(moviesListTrending) { movieClicked ->
                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, PeliculaSeleccionadaFragment.newInstance(movieClicked.id))
                transaction.addToBackStack(null)
                transaction.commit()
            }

            popularMoviesRv.adapter = moviesAdapter
        }
    }

    private fun initMoviesFilteredRv() {
        moviesAccess.listBestRatedMoviesWithGenres(genres) { updateFilteredMoviesList(it) }
    }
}