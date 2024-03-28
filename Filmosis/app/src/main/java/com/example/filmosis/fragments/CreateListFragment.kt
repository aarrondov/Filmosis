package com.example.filmosis.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.filmosis.R
import com.example.filmosis.adapters.ListsAdapter
import com.example.filmosis.dataclass.ListItem
import com.example.filmosis.init.FirebaseInitializer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class CreateListFragment : DialogFragment() {

    private val firestore = FirebaseInitializer.firestoreInstance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()

        val nombreListaTv: EditText = view.findViewById(R.id.createList_editTextTitle)
        val descripListaTv: EditText = view.findViewById(R.id.createList_editTextDescription)
        val guardarListaBtn: Button = view.findViewById(R.id.createList_buttonSave)

        guardarListaBtn.setOnClickListener {
            val titleLista = nombreListaTv.text.toString()
            if (titleLista.isNotEmpty()) {
                crearLista(titleLista, descripListaTv.text.toString())
            } else {
                Toast.makeText(requireContext(), "El título no puede estar vacío", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setup() {
        val cancelarBtn : Button = requireView().findViewById(R.id.createList_buttonCancel)

        cancelarBtn.setOnClickListener {
            dismiss()
        }


    }


    private fun crearLista(nombreLista: String, descripcionLista: String) {
        val email: String = FirebaseInitializer.authInstance.currentUser?.email!!

        // Generar un nombre aleatorio para el campo contenedor

        val uuid: UUID = UUID.randomUUID()
        val nombreCampoContenedor = "lista_${uuid}"

        // Crear un mapa con todos los campos, incluido listMovies
        val listaDatosAdicionales = hashMapOf<String, Any>(
            "$nombreCampoContenedor.listDate" to obtenerFechaActual(),
            "$nombreCampoContenedor.listDescription" to descripcionLista,
            "$nombreCampoContenedor.listId" to uuid.toString(), // Opción fija según tu ejemplo
            "$nombreCampoContenedor.listName" to nombreLista,
            "$nombreCampoContenedor.listMovies" to arrayListOf<Map<String, Any>>() // ArrayList vacío para listMovies
        )

        // Obtener la referencia al documento usando el email como ID
        val docRef = firestore.collection("lists").document(email)

        // Agregar los campos adicionales al documento existente usando update()
        docRef.update(listaDatosAdicionales)
            .addOnSuccessListener {
                dismiss()

                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStack()

                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, ListsFragment(), "LISTS_FRAGMENT")
                transaction.addToBackStack(null)
                transaction.commit()

            }
            .addOnFailureListener { e ->
                Log.w("CrearLista", "Error al agregar campos", e)
                Toast.makeText(requireContext(), "Error al agregar campos", Toast.LENGTH_SHORT).show()
            }
    }

    // Función para obtener la fecha actual en el formato deseado
    private fun obtenerFechaActual(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fechaActual = sdf.format(Date())
        Log.d("ObtenerFechaActual", "Fecha actual: $fechaActual")
        return fechaActual
    }



}