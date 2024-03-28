package com.example.filmosis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.filmosis.init.FirebaseInitializer

class ChangePasswordOnLogInActivity : AppCompatActivity() {
    lateinit var resetPassBtn : Button
    lateinit var backBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password_on_log_in)

        resetPassBtn = findViewById(R.id.changeOnLogIn_ChangePasswordButton)
        backBtn = findViewById(R.id.changeOnLogIn_returnButton)

        backBtn.setOnClickListener {
            finish()
        }

        resetPassBtn.setOnClickListener {
            val mailInput: EditText = findViewById(R.id.changeOnLogIn_emailEditText)
            val email = mailInput.text.toString()

            var confirmedEmail: String = ""
            if (email.isNotEmpty()) {
                confirmedEmail = email
            }

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Confirmación")
            alertDialogBuilder.setMessage("¿Estás seguro de que deseas restablecer la contraseña?")
            alertDialogBuilder.setPositiveButton("Sí") { dialog, which ->
                // Envía el correo de reseteo de contraseña si el usuario acepta
                FirebaseInitializer.authInstance.sendPasswordResetEmail(confirmedEmail).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@ChangePasswordOnLogInActivity, "Revisa tu correo electrónico", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@ChangePasswordOnLogInActivity, "Error ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
            alertDialogBuilder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

    }
}