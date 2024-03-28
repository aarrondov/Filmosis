package com.example.filmosis.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmosis.R
import com.example.filmosis.adapters.GridRecyclerServiceAdapter
import com.example.filmosis.adapters.GridRecyclerViewAdapter
import com.example.filmosis.adapters.ServicioAdapter
import com.example.filmosis.data.access.tmdb.MoviesAccess
import com.example.filmosis.dataclass.Network
import java.util.Random

class ServiceVerTodoFragment : Fragment() {

    private lateinit var recyclerViewservice: RecyclerView
    private lateinit var adapter: GridRecyclerServiceAdapter
    private var moviesAccess = MoviesAccess()
    private var services: ArrayList<Network> = ArrayList()


    private var ids: ArrayList<Int> = ArrayList<Int>().apply {
        addAll(listOf(49, 213, 2739,18,94,50,2740))
        addAll(generateRandomNumbers(100))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_service_ver_todo, container, false)

        recyclerViewservice = view.findViewById(R.id.recyclerViewVerTodoServicios)
        recyclerViewservice.layoutManager = GridLayoutManager(requireContext(),4)

        // Initialize the adapter here
        adapter = GridRecyclerServiceAdapter(services)
        recyclerViewservice.adapter = adapter

        // Fetch network details for each id
        for (id in ids) {
            moviesAccess.fetchNetworkDetails(id) {network ->
                if (network != null) {
                    services.add(network)
                    // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged()
                }
            }
        }

        val goBack : ImageButton = view.findViewById(R.id.goBack)
        goBack.setOnClickListener{
            parentFragmentManager.popBackStack()
        }

        return view
    }

    private fun generateRandomNumbers(count: Int): List<Int> {
        val random = Random()
        val randomNumbers = mutableListOf<Int>()
        repeat(count) {
            randomNumbers.add(random.nextInt(10000)) // Assuming you want numbers up to 10000
        }
        return randomNumbers
    }
}


