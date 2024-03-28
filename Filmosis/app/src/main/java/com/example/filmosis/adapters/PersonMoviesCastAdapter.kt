package com.example.filmosis.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmosis.R
import com.example.filmosis.config.DatosConexion
import com.example.filmosis.data.model.tmdb.Cast

class PersonMoviesCastAdapter(private val casts: List<Cast>, private val onCastClick: (Cast) -> Unit) : RecyclerView.Adapter<PersonMoviesCastAdapter.PersonMoviesCastViewHolder>() {
    class PersonMoviesCastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePic: ImageView = itemView.findViewById(R.id.itemPersonMovieCast_moviePic)
        val movieName: TextView = itemView.findViewById(R.id.itemPersonMovieCast_MovieTitle)

        val characterLabel: TextView = itemView.findViewById(R.id.itemPersonMovieCast_characterLabel)
        val characterName: TextView = itemView.findViewById(R.id.itemPersonMovieCast_Character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonMoviesCastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person_movie_cast, parent, false)
        return PersonMoviesCastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return casts.size
    }

    override fun onBindViewHolder(holder: PersonMoviesCastViewHolder, position: Int) {
        val cast = casts[position]

        if (!cast.poster_path.isNullOrEmpty()) {
            Glide.with(holder.moviePic.context).load(DatosConexion.TMDB_IMAGE_BASE_URL + cast.poster_path).into(holder.moviePic)
        } else {
            holder.moviePic.setImageResource(R.drawable.logofilmosispremium)
        }

        if (!cast.title.isNullOrEmpty()) {
            holder.movieName.text = cast.title
        } else if (!cast.original_title.isNullOrEmpty()) {
            holder.movieName.text = cast.original_title
        } else if (!cast.name.isNullOrEmpty()) {
            holder.movieName.text = cast.name
        } else {
            holder.movieName.text = cast.original_name
        }

        if (!cast.character.isNullOrEmpty()) {
            holder.characterName.text = cast.character
        } else {
            holder.characterLabel.visibility = View.GONE
            holder.characterName.visibility = View.GONE
        }

        holder.itemView.setOnClickListener { onCastClick.invoke(cast) }
    }
}