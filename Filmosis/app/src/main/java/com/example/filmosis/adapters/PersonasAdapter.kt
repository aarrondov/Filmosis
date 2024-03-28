package com.example.filmosis.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmosis.R
import com.example.filmosis.config.DatosConexion
import com.example.filmosis.data.model.tmdb.Crew


class PersonasAdapter(private val crewMembers: List<Crew>, private val onPersonClick: (Crew) -> Unit): RecyclerView.Adapter<PersonasAdapter.PersonRowViewHolder>() {

    class PersonRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personProfilePic: ImageView = itemView.findViewById(R.id.personSearched_profilePic)
        val personName: TextView = itemView.findViewById(R.id.personSearched_personTitle)
        val personType: TextView = itemView.findViewById(R.id.personSearched_personType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonRowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person_searched, parent, false)
        return PersonRowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return crewMembers.size
    }

    override fun onBindViewHolder(holder: PersonRowViewHolder, position: Int) {
        val crewMember = crewMembers[position]
        val imageUrl = DatosConexion.TMDB_IMAGE_BASE_URL + crewMember.profile_path

        holder.personName.text = crewMember.name
        holder.personType.text = crewMember.job

        if (!crewMember.profile_path.isNullOrEmpty()) {
            Glide.with(holder.personProfilePic.context)
                .load(imageUrl)
                .into(holder.personProfilePic)
        } else {
            holder.personProfilePic.setImageResource(R.drawable.logofilmosispremium)
        }

        holder.itemView.setOnClickListener { onPersonClick.invoke(crewMember) }
    }

}
