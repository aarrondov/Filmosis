package com.example.filmosis.utilities.firebase

object FirestoreImageManager {
    private var temporaryImageUri: String? = null

    fun getTemporaryImageUri(): String? {
        return temporaryImageUri
    }

    fun setTemporaryImageUri(uri: String?) {
        temporaryImageUri = uri
    }

    fun isTemporaryImageUriEmpty(): Boolean {
        return temporaryImageUri.isNullOrEmpty()
    }

    fun clearTemporaryImageUri() {
        temporaryImageUri = null
    }
}
