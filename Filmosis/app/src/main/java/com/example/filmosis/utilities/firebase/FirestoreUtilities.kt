package com.example.filmosis.utilities.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

object FirestoreUtilities {

    fun saveUserInFirestore(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth,
        username: String,
        email: String,
        fullName: String,
        birthDate: String,
        callback: (Boolean) -> Unit
    ) {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val userData = hashMapOf(
                "username" to username,
                "email" to email,
                "fullName" to fullName,
                "birthDate" to birthDate
            )

            firestore.collection("users").document(email) // Utilizar el nombre de usuario como clave
                .set(userData)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener { exception ->
                    callback(false)
                    Log.e("Firestore", "Error al guardar datos de usuario: ${exception.message}")
                }
        } ?: run {
            callback(false)
            Log.e("Firestore", "Error al obtener usuario actual")
        }
    }


    fun createUserListEntryInFirestore(firestore: FirebaseFirestore, email: String) {
        firestore.collection("lists").document(email)
            .set(hashMapOf<String, Any>())
    }
}