package com.example.filmosis.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmosis.R
import com.example.filmosis.fragments.GenreSelectedFragment
import com.example.filmosis.utilities.app.ResourcesMapping

class GenresCardViewsAdapter(private val genres: List<Pair<Int, String>>) : RecyclerView.Adapter<GenresCardViewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_cardview_template, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genreId = genres[position].first
        val genreName = genres[position].second
        holder.bind(genreId, genreName)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, GenreSelectedFragment.newInstance(genreId, genreName))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = genres.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewBackground: ImageView = itemView.findViewById(R.id.imageViewBackground)
        private val defaultDrawable = R.drawable.logofilmosispremium

        fun bind(genreId: Int, genreName: String) {
            itemView.findViewById<TextView>(R.id.textViewGenre).text = genreName

            val imageUrl = ResourcesMapping.genreImageMap[genreId]
            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(defaultDrawable)
                .into(imageViewBackground)
        }
    }
}
