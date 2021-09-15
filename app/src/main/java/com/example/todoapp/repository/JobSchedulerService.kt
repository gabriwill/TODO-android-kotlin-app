package com.example.todoapp.repository

import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.util.Log
import com.example.todoapp.utils.sendNotification
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class JobSchedulerService:JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.i("teste", "JobStart")
        val dataSource = DatabaseImpl(applicationContext)
        GlobalScope.launch {
            val todo = dataSource.getToDoByid(params?.extras?.getLong("id") ?: -1)
            if(todo != null) {
                val notificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.sendNotification(todo, applicationContext)
            }
        }
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.i("teste", "JobStop")
        return false
    }
}