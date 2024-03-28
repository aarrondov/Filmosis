package com.example.filmosis.adapters

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmosis.MainActivity
import com.example.filmosis.R
import com.example.filmosis.config.DatosConexion
import com.example.filmosis.data.model.tmdb.Person
import com.example.filmosis.utilities.tmdb.TmdbData
import de.hdodenhof.circleimageview.CircleImageView

class ListedPersonsAdapter(private val persons: List<Person>, private val onPersonClick: (Person) -> Unit): RecyclerView.Adapter<ListedPersonsAdapter.PersonRowViewHolder>() {
    class PersonRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personProfilePic : CircleImageView = itemView.findViewById(R.id.personSearched_profilePic)
        val personName : TextView = itemView.findViewById(R.id.personSearched_personTitle)
        val personType: TextView = itemView.findViewById(R.id.personSearched_personType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonRowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person_searched, parent, false)
        view.setOnCreateContextMenuListener { menu, v, menuInfo ->
            val activity = view.context as? MainActivity
            activity?.menuInflater?.inflate(R.menu.movie_row_menu, menu)
            menu.setHeaderTitle("Opciones de película")
        }
        return PersonRowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return persons.size
    }

    override fun onBindViewHolder(holder: PersonRowViewHolder, position: Int) {
        val person = persons[position]
        val imageUrl = DatosConexion.TMDB_IMAGE_BASE_URL + person.profile_path
        holder.personName.text = person.name

        if (!person.profile_path.isNullOrEmpty()) {
            Glide.with(holder.personProfilePic.context).load(imageUrl).into(holder.personProfilePic)
        } else {
            holder.personProfilePic.setImageResource(R.drawable.logofilmosispremium)
        }

        when (person.gender) {
            1 -> {
                if (person.known_for_department == "Directing") {
                    holder.personType.text = "Directora"
                } else if (person.known_for_department == "Acting") {
                    holder.personType.text = "Actriz"
                }
            }
            else -> {
                if (person.known_for_department == "Directing") {
                    holder.personType.text = "Director"
                } else if (person.known_for_department == "Acting") {
                    holder.personType.text = "Actor"
                }
            }
        }

        holder.itemView.setOnClickListener {onPersonClick.invoke(person)}
    }
}