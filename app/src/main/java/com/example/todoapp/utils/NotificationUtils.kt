package com.example.todoapp.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.model.ToDo



fun NotificationManager.sendNotification(todo: ToDo, applicationContext: Context) {
    // Create the content intent for the notification, which launches
    // this activity
    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        todo.id.toInt(),
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notifyImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.ic_notify_add_check_24
    )
    val uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_notification_timer_24)
        .setLargeIcon(notifyImage)
        .setContentTitle(todo.title)
        .setContentText(todo.title)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setSound(uriSound)
        .setLargeIcon(notifyImage)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(todo.id.toInt(), builder.build())
}
