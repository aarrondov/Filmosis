package com.example.filmosis.fragments



import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmosis.R
import com.example.filmosis.adapters.GridRecyclerViewAdapter
import com.example.filmosis.data.access.tmdb.MoviesAccess
import com.example.filmosis.data.model.tmdb.Movie
import com.google.gson.Gson


class VerTodoFragment : Fragment() {
    private val moviesAccess = MoviesAccess()

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewRecomendados: RecyclerView
    private lateinit var recyclerViewProximamentes: RecyclerView
    private lateinit var adapter: GridRecyclerViewAdapter

//    private lateinit var scrollView: ScrollView



    /**
     * Infla la vista del fragmento
     * Configura todas los RecyclerView
     * Boton para retroceder configurado (manera de eliminando de la pila)
     *
     * @param inflater Se emplea para inflar la vista
     * @param container El contenedor al que se pone la vista
     * @param savedInstanceState informacion previamente guardado del fragmento
     * @return la vista inflada del fragmento
     * **/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ver_todo, container, false)

        setupPopularMoviesRecyclerView(view)
        setupRecommendedMoviesRecyclerView(view)
        setupUpcomingMoviesRecyclerView(view)

        val buttonVolver: ImageButton = view.findViewById(R.id.goBack)
        buttonVolver.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }

    /**
     * Configura el RecyclerView para mostrar las peliculas populares
     *
     * @param view la vista del fragmento
     * **/
    private fun setupPopularMoviesRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewVerTodo)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        moviesAccess.listPopularMovies { movies ->
            setupMoviesRecyclerView(recyclerView, movies)
        }
    }

    /**
     * Configura el RecyclerView para mostrar las peliculas recomendadas
     *
     * @param view la vista del fragmento
     * **/
    private fun setupRecommendedMoviesRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewVerTodoRecomendados)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        moviesAccess.listRecommendedMovies(movieId = 438631) { movies ->
            setupMoviesRecyclerView(recyclerView, movies)
        }
    }

    /**
     * Configura el RecyclerView para mostrar las peliculas que saldran proximamente
     *
     * @param view la vista del fragmento
     * **/
    private fun setupUpcomingMoviesRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewVerTodoProximamente)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        moviesAccess.listUpcomingMovies { movies ->
            setupMoviesRecyclerView(recyclerView, movies)
        }
    }

    /**
     * Configura el RecyclerView para mostrar peliculas con el adaptador proporcionado
     *
     * @param recyclerView RecyclerView a configurar
     * @param movies lista de peliculas a mostrar
     * **/

    private fun setupMoviesRecyclerView(recyclerView: RecyclerView, movies: List<Movie>) {
        val adapter = GridRecyclerViewAdapter(movies) { movie ->
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, PeliculaSeleccionadaFragment.newInstance(movie.id))
            transaction.addToBackStack(null)
            transaction.commit()
        }
        recyclerView.adapter = adapter
    }



}