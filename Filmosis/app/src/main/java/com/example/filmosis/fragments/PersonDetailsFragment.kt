package com.example.filmosis.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmosis.R
import com.example.filmosis.adapters.ListedPersonsAdapter
import com.example.filmosis.adapters.PersonMoviesCastAdapter
import com.example.filmosis.config.DatosConexion
import com.example.filmosis.data.access.tmdb.PersonsAccess
import com.example.filmosis.data.model.tmdb.Cast
import com.example.filmosis.data.model.tmdb.Person

class PersonDetailsFragment : Fragment() {
    private val personsAccess = PersonsAccess()

    companion object {
        private const val ARG_PERSON_ID = "personId"

        fun newInstance(personId: Int): PersonDetailsFragment {
            val fragment = PersonDetailsFragment()
            val args = Bundle()

            args.putInt(ARG_PERSON_ID, personId)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        val personId = arguments?.getInt(ARG_PERSON_ID)

        if (personId != null) {
            initPersonInfo(personId)
            initPersonMoviesRv(personId)
        }

    }

    private fun initPersonInfo(personId: Int) {
        val personProfilePic: ImageView = requireView().findViewById(R.id.personDetails_personProfilePic)
        val personName: TextView = requireView().findViewById(R.id.personDetails_nameTextView)
        val personDepartment: TextView = requireView().findViewById(R.id.personDetails_departmentTextView)
        val personBirthPlace: TextView = requireView().findViewById(R.id.personDetails_birthPlaceTextView)
        val personBirthDay: TextView = requireView().findViewById(R.id.personDetails_birthdayTextView)
        val personBiography: TextView = requireView().findViewById(R.id.personDetails_biography)

        personsAccess.getPersonDetails(personId) {
            if (!it.profile_path.isNullOrEmpty()) {
                Glide.with(requireView()).load(DatosConexion.TMDB_IMAGE_BASE_URL + it.profile_path).into(personProfilePic)
            } else {
                personProfilePic.setImageResource(R.drawable.logofilmosispremium)
            }

            personName.text = it.name
            personDepartment.text = it.known_for_department
            personBirthPlace.text = it.place_of_birth
            personBirthDay.text = it.birthday
            personBiography.text = it.biography
        }
    }

    private fun initPersonMoviesRv(personId: Int) {
        val personMoviesRv: RecyclerView = requireView().findViewById(R.id.personDetails_personMoviesRv)
        personMoviesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val castList: ArrayList<Cast> = ArrayList()

        personsAccess.getPersonCombinedCredits(personId) {
            val sortedList = it.sortedByDescending { cast -> cast.vote_count }

            sortedList.forEach { cast ->
                castList.add(cast)
            }

            val castsAdapter = PersonMoviesCastAdapter(castList) { castClicked ->
                // TODO
                // LLEVAR A PANTALLA DE DETALLES DE PELICULA...
            }

            personMoviesRv.adapter = castsAdapter
        }
    }
}