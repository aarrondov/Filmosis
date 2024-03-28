package com.example.filmosis.adapters

import android.content.Context
import com.example.filmosis.data.model.tmdb.Cast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmosis.R
import com.example.filmosis.config.DatosConexion
import com.example.filmosis.data.model.tmdb.CastX
import com.example.filmosis.data.model.tmdb.Crew
import java.util.Locale


class ActoresAdapter(private val context: Context, private val castMembers: List<CastX>, private val onPersonClick: (CastX) -> Unit): RecyclerView.Adapter<ActoresAdapter.ActoresRowViewHolder>() {

    class ActoresRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personProfilePic: ImageView = itemView.findViewById(R.id.personSearched_profilePic)
        val personName: TextView = itemView.findViewById(R.id.personSearched_personTitle)
        val personType: TextView = itemView.findViewById(R.id.personSearched_personType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActoresRowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person_searched, parent, false)
        return ActoresRowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return castMembers.size
    }

    override fun onBindViewHolder(holder: ActoresRowViewHolder, position: Int) {
        val castMember = castMembers[position]
        val imageUrl = DatosConexion.TMDB_IMAGE_BASE_URL + castMember.profile_path

        holder.personName.text = castMember.name

        when (castMember.gender) {
            1 -> {
                if (castMember.known_for_department == "Directing") {
                    holder.personType.text = context.getString(R.string.directora)
                } else if (castMember.known_for_department == "Acting") {
                    holder.personType.text = context.getString(R.string.actriz)
                }
            }
            else -> {
                if (castMember.known_for_department == "Directing") {
                    holder.personType.text = "Director"
                } else if (castMember.known_for_department == "Acting") {
                    holder.personType.text = "Actor"
                }
            }
        }

        if (!castMember.profile_path.isNullOrEmpty()) {
            Glide.with(holder.personProfilePic.context)
                .load(imageUrl)
                .into(holder.personProfilePic)
        } else {
            holder.personProfilePic.setImageResource(R.drawable.logofilmosispremium)
        }

        holder.itemView.setOnClickListener { onPersonClick.invoke(castMember) }
    }

}
