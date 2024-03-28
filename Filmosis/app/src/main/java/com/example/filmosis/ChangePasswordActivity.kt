package com.example.filmosis

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.filmosis.init.FirebaseInitializer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException


class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var btnChangePass : Button
    private lateinit var btnCancel : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        btnChangePass = findViewById(R.id.btnConfirm)
        btnCancel = findViewById(R.id.btnCancel)


        btnCancel.setOnClickListener{
            finish()
        }

        btnChangePass.setOnClickListener {
            val oldPassword = findViewById<TextView>(R.id.changeFromProf_currentPass).text.toString()
            val newPassword = findViewById<TextView>(R.id.changeFromProf_newPass).text.toString()
            val newPasswordConfirmed = findViewById<TextView>(R.id.changeFromProf_newPassConfirm).text.toString()

            val user = FirebaseInitializer.authInstance.currentUser
            val email = user?.email

            if (email != null) {
                FirebaseInitializer.authInstance.signInWithEmailAndPassword(email, oldPassword)
                    .addOnCompleteListener { signInTask ->
                        if (signInTask.isSuccessful) {
                            // La contraseña antigua coincide, ahora puedes proceder a cambiar la contraseña
                            if (newPassword == newPasswordConfirmed && newPassword.length > 7) {
                                user.updatePassword(newPassword)
                                    .addOnCompleteListener { updatePasswordTask ->
                                        if (updatePasswordTask.isSuccessful) {
                                            Toast.makeText(applicationContext, "Contraseña actualizada exitosamente", Toast.LENGTH_SHORT).show()
                                            finish() // Cierra la actividad actual
                                        } else {
                                            Toast.makeText(applicationContext, "Error al actualizar la contraseña: ${updatePasswordTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(applicationContext, "Las contraseñas no coinciden o no cumplen con los requisitos de seguridad", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "La contraseña antigua es incorrecta", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        if (exception is FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(applicationContext, "La contraseña antigua es incorrecta", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, "Error al iniciar sesión: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(applicationContext, "No se pudo obtener el correo electrónico del usuario", Toast.LENGTH_SHORT).show()
                finish() // Cierra la actividad si no se pudo obtener el correo electrónico
            }
        }

    }
}