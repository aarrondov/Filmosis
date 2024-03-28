package com.example.filmosis.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmosis.R
import com.example.filmosis.config.DatosConexion
import com.example.filmosis.data.model.tmdb.Movie

class CarouselMoviesAdapter(private val movies: List<Movie>, private val onMovieClick: (Movie) -> Unit) : RecyclerView.Adapter<CarouselMoviesAdapter.CarouselMoviesViewHolder>() {
    class CarouselMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImageView : ImageView = itemView.findViewById(R.id.carousel_posterImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carousel_movie_poster_item, parent, false)
        return CarouselMoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: CarouselMoviesViewHolder, position: Int) {
        val movie = movies[position]
        val imageUrl = DatosConexion.TMDB_IMAGE_BASE_URL + movie.poster_path

        Glide.with(holder.movieImageView.context).load(imageUrl).into(holder.movieImageView)
        holder.itemView.setOnClickListener {onMovieClick.invoke(movie)}
    }
}