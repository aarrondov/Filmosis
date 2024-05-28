package com.example.filmosis.utilities.app

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.example.filmosis.MainActivity
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

fun downloadImage(context: Context, imageUrl: String, fileName: String) {
    Thread {
        try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val input: InputStream = connection.inputStream
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File(storageDir, fileName)

            val outputStream = FileOutputStream(imageFile)
            input.copyTo(outputStream)
            outputStream.close()
            input.close()

            (context as MainActivity).runOnUiThread {
                Toast.makeText(context, "Imagen descargada en: ${imageFile.absolutePath}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            (context as MainActivity).runOnUiThread {
                Toast.makeText(context, "Error al descargar la imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }.start()
}
