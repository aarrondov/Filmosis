package com.example.filmosis.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmosis.R
import com.example.filmosis.data.model.tmdb.Movie
import com.example.filmosis.fragments.PeliculaSeleccionadaFragment

class GridRecyclerViewAdapter(private val movies: List<Movie>, private val onMovieClick: (Movie) -> Unit) : RecyclerView.Adapter<GridRecyclerViewAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { onMovieClick.invoke(movie) }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val iconoPelicula: ImageView = itemView.findViewById(R.id.icono_pelicula)
        private val nombrePelicula: TextView = itemView.findViewById(R.id.nombre_pelicula)

        fun bind(pelicula: Movie) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${pelicula.poster_path}")
                .into(iconoPelicula)
            nombrePelicula.text = pelicula.title
        }
    }
}



