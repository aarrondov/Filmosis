package com.example.filmosis.init

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object FirebaseInitializer {
    val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    val authInstance: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val firebaseStorageInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }
}