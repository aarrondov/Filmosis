package com.example.filmosis.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmosis.R
import com.example.filmosis.adapters.ListsAdapter
import com.example.filmosis.dataclass.ListItem
import com.example.filmosis.init.FirebaseInitializer
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


class FilmosisListsFragment : Fragment() {

    private val firestore: FirebaseFirestore = FirebaseInitializer.firestoreInstance

    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_popular_lists, container, false)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        val username: String? = FirebaseInitializer.authInstance.currentUser?.email

//        val createListBtn : Button? = rootView?.findViewById(R.id.list_createListBtn)
//
//        createListBtn?.setOnClickListener {
//            crearLista()
//        }

        if (username != null) {
//            fetchDocument(username)
            fetchDocument()
        } else {
            Log.d("ListActivity", "No se pudo obtener el nombre de usuario.")
        }
    }

    private fun fetchDocument(username: String) {
        val docRef = firestore.collection("lists").document("ListasPopulares")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val data = document.data
                    if (data != null) {
                        val keys = data.keys.toList()
                        Log.d("ListActivity", keys.toString())

                        val listOfLists = keys.map { key ->
                            val listData = data[key] as? Map<*, *>
                            val listId = listData?.get("listId").toString()
                            Log.d("ListActivity", "listId: $listId")
                            val listName = listData?.get("listName") as? String
                            Log.d("ListActivity", "listName: ${listName.toString()}")
                            val listDescription = listData?.get("listDescription") as? String
                            Log.d("ListActivity", "listDescription: ${listDescription.toString()}")
                            val listDate = listData?.get("listDate") as? String
                            Log.d("ListActivity", "listDate: ${listDate.toString()}")

                            ListItem(listId, listName.toString(), listDescription.toString(), listDate.toString())
                        }.toMutableList()

                        rootView?.findViewById<ProgressBar>(R.id.lists_progressCirclePopular)?.visibility = View.GONE

                        if (listOfLists.isEmpty()) {
                            rootView?.findViewById<TextView>(R.id.lists_errorTextViewPopular)?.text =
                                getString(
                                    R.string.no_lista
                                )
                        } else {
                            rootView?.findViewById<TextView>(R.id.lists_errorTextViewPopular)?.visibility = View.GONE
                            initListsRv(listOfLists)
                        }


                    } else {
                        Log.d("ListActivity", "El documento está vacío para el usuario $username.")
                    }
                } else {
                    Log.d("ListActivity", "No se encontró ningún documento para el usuario $username.")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("ListActivity", "Error al obtener el documento: $exception")
            }
    }

    private fun fetchDocument() {
        val docRef = firestore.collection("lists").document("ListasPopulares")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val data = document.data
                    if (data != null) {
                        val keys = data.keys.toList()

                        val listOfLists = keys.map { key ->
                            val listData = data[key] as? Map<*, *>
                            val listId = listData?.get("listId").toString()
                            Log.d("ListActivity", "listId: $listId")
                            val listName = listData?.get("listName") as? String
                            Log.d("ListActivity", "listName: ${listName.toString()}")
                            val listDescription = listData?.get("listDescription") as? String
                            Log.d("ListActivity", "listDescription: ${listDescription.toString()}")
                            val listDate = listData?.get("listDate") as? String
                            Log.d("ListActivity", "listDate: ${listDate.toString()}")

                            ListItem(listId, listName.toString(), listDescription.toString(), listDate.toString())
                        }.toMutableList()

                        rootView?.findViewById<ProgressBar>(R.id.lists_progressCirclePopular)?.visibility = View.GONE

                        if (listOfLists.isEmpty()) {
                            rootView?.findViewById<TextView>(R.id.lists_errorTextViewPopular)?.text =
                                getString(
                                    R.string.no_lista
                                )
                        } else {
                            rootView?.findViewById<TextView>(R.id.lists_errorTextViewPopular)?.visibility = View.GONE
                            initListsRv(listOfLists)
                        }


                    } else {
                        Log.d("ListActivity", "El documento está vacío.")
                    }
                } else {
                    Log.d("ListActivity", "No se encontró ningún documento para el usuario popularLists.")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("ListActivity", "Error al obtener el documento: $exception")
            }
    }



    private fun initListsRv(lists: MutableList<ListItem>) {
        val rv = rootView?.findViewById<RecyclerView>(R.id.lists_recyclerViewPopular)
        rv?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv?.adapter = ListsAdapter(
            lists,
            onListClick = {
                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, MoviesInListFragment.newInstance(it.listId,"PopularListsFragment"))
                Log.d("ListActivity","asdasda")
                transaction.addToBackStack(null)
                transaction.commit()
            },
            onDeleteClick = {

            }
        )
    }

    private fun deleteListFromFirestore(listId: String) {
        val userEmail = FirebaseInitializer.authInstance.currentUser?.email.toString()
        val listsRef = firestore.collection("lists").document(userEmail)

        val updates = hashMapOf<String, Any>(
            "lista_$listId" to FieldValue.delete()
        )

        listsRef.update(updates)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Lista eliminada", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Error al eliminar la lista: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


    private fun crearLista() {
        val dialog = CreateListFragment()
        dialog.show(requireActivity().supportFragmentManager, "createListFragment")
    }


}