package com.example.filmosis.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.filmosis.AuthActivity
import com.example.filmosis.ChangePasswordActivity
import com.example.filmosis.R
import com.example.filmosis.init.FirebaseInitializer
import com.example.filmosis.utilities.firebase.FirestoreImageManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

enum class ProviderType {
    BASIC,
    GOOGLE
}

class UserFragment : Fragment() {
    private lateinit var profilePic : CircleImageView
    private var ultimoTiempoRequest: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup
        val prefs = requireActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)
        val username = prefs.getString("username",null)
        setup(view, email ?: "", provider ?: "",username?:"")
    }

    private fun setup(view: View, email: String, provider: String,username : String) {
        val fullNameTextView: TextView = view.findViewById(R.id.user_userFullName)
        val emailTextView: TextView = view.findViewById(R.id.emailTextView)
        val userTextView : TextView = view.findViewById(R.id.userTextView)
        val logOutButton: Button = view.findViewById(R.id.logOutButton)
        val changePassButton : Button = view.findViewById(R.id.changePassButton)
        val resetPassButon : Button = view.findViewById(R.id.resetPassButton)
        val editPic : ImageView = view.findViewById(R.id.fragmentUser_editImage)
        profilePic = view.findViewById(R.id.logoImageView)

        val prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val fullname: String? = prefs?.getString("fullname", "")

        if (FirestoreImageManager.isTemporaryImageUriEmpty()) {
            val storageReference = FirebaseInitializer.firebaseStorageInstance.reference

            val username : String? = requireActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)?.getString("username","")
            if (!username.isNullOrBlank()) {
                val profilePicSrc: String = "profilepic_$username.jpg"

                val imagesRef = storageReference.child("images")

                imagesRef.listAll()
                    .addOnSuccessListener { listResult ->
                        for (item in listResult.items) {
                            if (item.name == profilePicSrc) {
                                item.downloadUrl.addOnSuccessListener { uri ->
                                    val imageUrl = uri.toString()
                                    FirestoreImageManager.setTemporaryImageUri(imageUrl)
                                    Glide.with(view).load(imageUrl).into(profilePic)
                                }.addOnFailureListener { exception ->
                                    Log.d("Profile Pic", "Error al obtener la URL de la imagen", exception)
                                }
                                return@addOnSuccessListener
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("Profile Pic", "Error al verificar la existencia del archivo en Firebase Storage", exception)
                    }
            }
        } else {
            Glide.with(view).load(FirestoreImageManager.getTemporaryImageUri()).into(profilePic)
        }



        fullNameTextView.text = fullname
        emailTextView.text = email
        userTextView.text = username

        logOutButton.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setPositiveButton("Sí") { _, _ ->
                    // Borrado de datos
                    val prefs =
                        requireActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
                            .edit()
                    prefs.clear()
                    prefs.apply()

                    FirebaseAuth.getInstance().signOut()

                    showAuthActivity()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss() // Cierra el diálogo sin hacer nada
                }
                .show()
        }

        changePassButton.setOnClickListener {
            val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        resetPassButon.setOnClickListener {
            val tiempoActual = System.currentTimeMillis()

            if (tiempoActual - ultimoTiempoRequest >= 60000) { // 60000 milisegundos = 1 minuto
                val user = FirebaseInitializer.authInstance.currentUser

                val email = user?.email
                var confirmedEmail : String = ""
                if (email != null) {
                    if (email.isNotEmpty()) {
                        confirmedEmail = email.toString()
                    }
                }

                FirebaseInitializer.authInstance.sendPasswordResetEmail(confirmedEmail).addOnCompleteListener{
                        task ->
                    if (task.isSuccessful) {
                        val builder = AlertDialog.Builder(requireContext())

                        builder.setTitle("Reestablecimiento de constraseña")
                        builder.setMessage("Revisa tu correo electrónico: $email")

                        builder.setPositiveButton("Aceptar") { dialog, _  ->
                            dialog.dismiss()
                        }

                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.show()
                    }else {
                        Toast.makeText(requireContext(),"Error ${task.exception?.message}",
                            Toast.LENGTH_LONG).show()
                    }
                }

                ultimoTiempoRequest = tiempoActual
            } else {
                Toast.makeText(requireContext(),"Espera 1 minuto para volver a reenviar el correo de restablecimiento de contraseña.",
                    Toast.LENGTH_LONG).show()
            }
        }

        editPic.setOnClickListener {
            val pickImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            Log.d("Foto seleccionada",selectedImageUri.toString())
            profilePic.setImageURI(selectedImageUri)
            val drawerNavigationView: NavigationView = requireActivity().findViewById(R.id.main_drawerNavigationView)
            Glide.with(requireContext()).load(selectedImageUri).into(drawerNavigationView.getHeaderView(0).findViewById(R.id.drawerHeader_profilePic))
            FirestoreImageManager.setTemporaryImageUri(selectedImageUri.toString())
            val prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
            if (selectedImageUri != null && prefs?.getString("username","") != null) {
                uploadImageToFirebaseStorage(selectedImageUri, prefs.getString("email","")!!)
            }
        }
    }

    private fun uploadImageToFirebaseStorage(imageUri: Uri, email: String) {
        // Obtener una referencia al Storage de Firebase
        val storageReference = FirebaseInitializer.firebaseStorageInstance.reference

        // Crear una referencia para la imagen en el Storage
        // Utiliza el nombre de usuario para generar el nombre del archivo
        val imageName = "profilepic_$email.jpg"
        val imagesRef = storageReference.child("images/$imageName")

        // Subir la imagen al Storage
        val uploadTask = imagesRef.putFile(imageUri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Imagen subida exitosamente
            Log.d("Upload", "Image uploaded successfully")
            // Mostrar un Toast para notificar al usuario
            Toast.makeText(requireContext(), "Imagen subida exitosamente", Toast.LENGTH_SHORT).show()
            // Aquí puedes obtener la URL de la imagen subida
            imagesRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                Log.d("Image URL", imageUrl)
                // Aquí puedes hacer lo que necesites con la URL de la imagen, como guardarla en una base de datos Firestore
            }.addOnFailureListener { exception ->
                // Error al obtener la URL de la imagen
                Log.e("Image URL", "Error getting image URL", exception)
            }
        }.addOnFailureListener { exception ->
            // Error al subir la imagen
            Log.e("Upload", "Image upload failed", exception)
            // Mostrar un Toast para notificar al usuario
            Toast.makeText(requireContext(), "Error al subir la imagen", Toast.LENGTH_SHORT).show()
        }
    }



    private fun showAuthActivity() {
        val authIntent = Intent(activity, AuthActivity::class.java)

        startActivity(authIntent)
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

}