package com.example.filmosis

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.filmosis.fragments.ProviderType
import com.example.filmosis.init.FirebaseInitializer
import com.example.filmosis.utilities.firebase.FirestoreUtilities
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Locale

class SignUpActivity : AppCompatActivity() {

    private val firestore: FirebaseFirestore = FirebaseInitializer.firestoreInstance
    private val auth: FirebaseAuth = FirebaseInitializer.authInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setup()
    }

    private fun setup() {
        val emailEditText: EditText = findViewById(R.id.signUp_emailEditText)
        val passwordEditText: EditText = findViewById(R.id.signUp_passwordEditText)
        val usernameEditText: EditText = findViewById(R.id.signUp_usernameEditText)
        val nameEditText: EditText = findViewById(R.id.signUp_nameEditText)
        val surnamesEditText: EditText = findViewById(R.id.signUp_surnameEditText)
        val bornDateEditText = findViewById<EditText>(R.id.signUp_bornDateEditText)
        val signUpButton: Button = findViewById(R.id.signUp_signUpButton)
        val returnButton: Button = findViewById(R.id.signUp_returnButton)

        signUpButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty() && usernameEditText.text.isNotEmpty()) {
                if (passwordEditText.text.toString().length > 6) {
                    val username = usernameEditText.text.toString()

                    val users = firestore.collection("users")

                    users.whereEqualTo("username", username)
                        .get().addOnSuccessListener { documents ->
                            if(!documents.isEmpty){
                                showAlert("El nombre de usuario introducido ya está en uso por otra cuenta")
                            } else {
                                auth.createUserWithEmailAndPassword(
                                    emailEditText.text.toString(),
                                    passwordEditText.text.toString()
                                ).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        FirestoreUtilities.saveUserInFirestore(firestore, auth, usernameEditText.text.toString(), emailEditText.text.toString(), nameEditText.text.toString() + " " + surnamesEditText.text.toString(),bornDateEditText.text.toString()) { success ->
                                            if (success) {
                                                FirestoreUtilities.createUserListEntryInFirestore(firestore, auth.currentUser?.email.toString())
                                                guardarDatos(emailEditText.text.toString(), ProviderType.BASIC.toString(), usernameEditText.text.toString(), nameEditText.text.toString() + " " + surnamesEditText.text.toString())
                                                showMain()
                                            } else {
                                                showAlert("Error al guardar el usuario en la base de datos")
                                            }
                                        }
                                    } else {
                                        if (task.exception is FirebaseAuthUserCollisionException) {
                                            showAlert("El email introducido ya está en uso por otra cuenta")
                                        } else {
                                            showAlert(task.exception.toString())
                                        }
                                    }
                                }
                            }
                        }
                } else {
                    Toast.makeText(this@SignUpActivity, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                }
            } else {
                showAlert("Rellena todos los campos porfavor.")
            }
        }

        returnButton.setOnClickListener {
            returnToAuthScreen()
        }

        bornDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    // Actualizar el texto del EditText con la fecha seleccionada
                    val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                    bornDateEditText.setText(selectedDate)
                },
                year,
                month,
                dayOfMonth
            )

            datePickerDialog.show()
        }
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

    private fun guardarDatos(email: String, provider: String, username : String, fullname: String) {
        // Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        Log.d("signUp",username)
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.putString("username", username)
        prefs.putString("fullname", fullname)
        prefs.apply()
    }

    private fun showAlert(error: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al crear la cuenta: $error")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}