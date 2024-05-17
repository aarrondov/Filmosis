package com.example.filmosis.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.filmosis.R
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HelpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HelpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help, container, false)

        // Obtener referencias a los elementos de la vista
        val spinner = view.findViewById<Spinner>(R.id.help_fragment_spinner)
        val subjectEditText = view.findViewById<EditText>(R.id.help_fragment_subject)
        val messageEditText = view.findViewById<EditText>(R.id.help_fragment_message)
        val sendButton = view.findViewById<Button>(R.id.help_fragment_send_button)

        // Configurar el listener del botón de enviar
        sendButton.setOnClickListener {
            // Obtener los valores de los campos del formulario
            val selectedTopic = spinner.selectedItem.toString()
            val subject = subjectEditText.text.toString().trim()
            val message = messageEditText.text.toString().trim()

            // Verificar si los campos están vacíos
            if (subject.isEmpty() || message.isEmpty()) {
                // Mostrar un diálogo de advertencia
                showEmptyFieldsDialog()
            } else {
                // Crear el intent para enviar el correo electrónico
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:filmosisadm@gmail.com?subject=${Uri.parse(subject)}&body=${Uri.parse(message)}") // Solo aplicaciones de correo electrónico
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, message)
                }

                // Verificar si hay alguna aplicación que pueda manejar el intent
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), getString(R.string.fill),Toast.LENGTH_LONG).show()
                }
            }
        }

        return view
    }

    private fun showEmptyFieldsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Campos vacíos")
            .setMessage("Por favor, completa todos los campos antes de enviar el mensaje.")
            .setPositiveButton("Entendido") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}