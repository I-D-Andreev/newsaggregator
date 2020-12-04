package com.example.ivanandreev.newsaggregator.helpers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.ivanandreev.newsaggregator.LoginActivity
import com.example.ivanandreev.newsaggregator.MainActivity
import com.example.ivanandreev.newsaggregator.R

class NotificationSender(val context: Context) {
    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val user = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser


    init {
        createNotificationChannel()
    }

    fun sendNotification(title: String, body: String) {
        notificationManager.notify(NOTIFICATION_ID, createNotification(title, body))
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.setShowBadge(true)
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun createNotification(title: String, body: String): Notification {
        val intent: Intent = if (user != null) {
            println("!!! ${user.email}")
            Intent(context, MainActivity::class.java)
        } else {
            Intent(context, LoginActivity::class.java)
        }
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_baseline_news)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "com.example.ivanandreev.newsaggregator.MAIN"
        const val NOTIFICATION_CHANNEL_NAME = "Main"
        const val NOTIFICATION_ID = 101

    }
}