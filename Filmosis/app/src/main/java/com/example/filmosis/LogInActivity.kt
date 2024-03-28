package com.example.filmosis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.filmosis.fragments.ProviderType
import com.example.filmosis.init.FirebaseInitializer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore

class LogInActivity : AppCompatActivity() {

    private val firestore: FirebaseFirestore = FirebaseInitializer.firestoreInstance
    private val auth: FirebaseAuth = FirebaseInitializer.authInstance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        setup()
    }

    private fun setup() {
        val usernameEditText: EditText = findViewById(R.id.signUp_emailEditText)
        val passwordEditText: EditText = findViewById(R.id.signUp_passwordEditText)
        val logInBtn: Button = findViewById(R.id.logInButton)
        val returnButton: Button = findViewById(R.id.logIn_returnButton)
        val forgotPass: TextView = findViewById(R.id.logIn_forgotTextView)

        logInBtn.setOnClickListener {
            if (usernameEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                val username = usernameEditText.text.toString()
                //Consultamos a nuestra base de datos si existe algun usuario con el nombre introducido

                val userReferencia = firestore.collection("users")//cogemos la tabla de users

                userReferencia.whereEqualTo("name", username)//comparamos si existe el nombre de ususario
                    .get().addOnSuccessListener { documents ->//Si si existen que ejecute el resto de codigo
                        if(!documents.isEmpty){
                            // si ha encontrado un usuario con emai y nombre de usuario correspondiente que siga
                            // cogemos el email asociado con el nombre de usuario introducido en la coleccion de la db
                            var userEmail = ""
                            var userFullName = ""
                            for (document in documents){
                                userEmail = document.getString("email").toString()
                                userFullName = document.getString("fullName").toString()
                            }

                            if (!userEmail.isNullOrBlank()) {
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, passwordEditText.text.toString()).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {// Anadimos a la base de datos si to'do sale bien
                                        guardarDatos(userEmail, ProviderType.BASIC.toString(), username, userFullName)
                                        showMain()
                                    } else {
                                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                            showAlert("Nombre de usuario o contraseña incorrectos.")
                                        } else {
                                            showAlert(task.exception.toString())
                                        }
                                    }
                                }
                            } else {
                                // Alert de debug... no debería estar en release
                                showAlert("Error al obtener el email del usuario.")
                            }
                        } else {
                            // No se ha encontrado el usuario con el nombre introducido...
                            showAlert("Nombre de usuario o contraseña incorrectos.")
                        }
                    }
                    .addOnFailureListener { exception -> //en caso que haya dado error
                        showAlert("Error al verificar las credenciales: ${exception.message}")
                    }

            } else {
                showAlert("Rellena todos los campos por favor")
            }
        }

        returnButton.setOnClickListener {
            returnToAuthScreen()
        }

        forgotPass.setOnClickListener {
            val forgotIntent = Intent(this, ChangePasswordOnLogInActivity::class.java)
            startActivity(forgotIntent)
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

    private fun guardarDatos(email: String, provider: String, username : String, fullname : String) {
        // Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.putString("username", username)
        prefs.putString("fullname", fullname)
        prefs.apply()
    }

    private fun showMain() {
        //Intent creado para ir al MainActivity
        val homeIntent = Intent(this, MainActivity::class.java)
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(homeIntent)
    }

    private fun returnToAuthScreen() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}