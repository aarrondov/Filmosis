package com.example.filmosis.adapters

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmosis.dataclass.Pelicula
import com.example.filmosis.dataclass.Servicio


class CustomAdapter(private val context: Context, private val peliculas: List<Pelicula>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(com.example.filmosis.R.layout.row_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val peliculas = peliculas[position]
        holder.bind(peliculas)
    }

    override fun getItemCount(): Int {
        return peliculas.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconoPelicula: ImageView = itemView.findViewById(com.example.filmosis.R.id.icono_pelicula)
        private val nombrePelicula: TextView = itemView.findViewById(com.example.filmosis.R.id.nombre_pelicula)

        fun bind(pelicula: Pelicula) {
            iconoPelicula.setImageResource(pelicula.icono)
            nombrePelicula.text = pelicula.nombre
        }
    }
}

