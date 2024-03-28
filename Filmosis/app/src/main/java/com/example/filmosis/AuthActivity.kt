package com.example.filmosis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.filmosis.fragments.ProviderType
import com.example.filmosis.init.FirebaseInitializer
import com.example.filmosis.utilities.firebase.FirestoreUtilities
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class AuthActivity : AppCompatActivity() {

    private val firestore: FirebaseFirestore = FirebaseInitializer.firestoreInstance
    private val auth: FirebaseAuth = FirebaseInitializer.authInstance
    private var loadingDialog: AlertDialog? = null

    private val startGoogleSignInActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account.idToken)
                } catch (e: ApiException) {
                    showAlert("Error al procesar el inicio de sesión con Google.")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Setup
        session()
        setup()
    }

    override fun onStart() {
        super.onStart()
        findViewById<ConstraintLayout>(R.id.mainLayout).visibility = View.VISIBLE
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email != null && provider != null) {
            findViewById<ConstraintLayout>(R.id.mainLayout).visibility = View.INVISIBLE
            showMain()
        }
    }

    private fun setup() {
        FirebaseApp.initializeApp(this)

        val signUpButton: Button = findViewById(R.id.signUpButton)
        val logInButton: Button = findViewById(R.id.logInButton)
        val googleSignInButton: Button = findViewById(R.id.googleSignInButton)

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        logInButton.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        googleSignInButton.setOnClickListener {
//            showLoadingDialog()

            // Configuración

            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)

            googleClient.signOut()

            startGoogleSignInActivityForResult.launch(googleClient.signInIntent)
        }
    }

    private fun showAlert(error: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario: $error")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showLoadingDialog() {
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.loading_dialog, null)

        loadingDialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()

        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    private fun showMain() {
        //Intent creado para ir al MainActivity
        val homeIntent = Intent(this, MainActivity::class.java)
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(homeIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    FirestoreUtilities.saveUserInFirestore(firestore, auth, auth.currentUser?.displayName.toString(), auth.currentUser?.email.toString(), auth.currentUser?.displayName.toString(), "") { success ->
                        if (success) {
                            FirestoreUtilities.createUserListEntryInFirestore(firestore, auth.currentUser?.email.toString())
                            guardarDatos(auth.currentUser?.email ?: "", ProviderType.GOOGLE.toString(), auth.currentUser?.displayName ?: "", auth.currentUser?.displayName ?: "")
                            showMain()
                        } else {
                            showAlert("Error al guardar el usuario en la base de datos")
                        }
                    }
                } else {
                    showAlert(task.exception.toString())
                }
            }
    }

    private fun guardarDatos(email: String, provider: String, username : String, fullname: String) {
        // Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.putString("username", username)
        prefs.putString("fullname", fullname)
        prefs.apply()
    }

//    private fun hashPassword(password: String): String {
//        // Aquí debes implementar tu lógica de cifrado, por ejemplo utilizando bcrypt o SHA-256
//        // Esta es solo una implementación de ejemplo utilizando la función de hash SHA-256
//        val digest = MessageDigest.getInstance("SHA-256")
//        val encodedHash = digest.digest(password.toByteArray(StandardCharsets.UTF_8))
//        return Base64.getEncoder().encodeToString(encodedHash)
//    }

}