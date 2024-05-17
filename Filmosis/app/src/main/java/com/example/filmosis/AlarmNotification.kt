package com.example.filmosis

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.filmosis.fragments.HomeFragment

class AlarmNotification : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
    }
    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("title") ?: "Default Title"
        val content = intent?.getStringExtra("content") ?: "Default Content"
        createSimpleNotification(context, title, content)
    }

    fun createSimpleNotification(context: Context, title: String, description: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else 0
        Log.d("flag", flag.toString())

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        val notification = NotificationCompat.Builder(context, HomeFragment.MY_CHANNEL_ID)
            .setSmallIcon(R.drawable.logofilmosispremium)
            .setContentTitle(title)
            .setContentText(description)
            .setStyle(NotificationCompat.BigTextStyle().bigText(description))
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID,notification)
    }
}