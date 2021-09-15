package com.example.todoapp.repository

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.PersistableBundle
import android.util.Log
import com.example.todoapp.model.ToDo

class TodoNotificationScheduler {
    companion object{
        fun scheduleTodo(context: Context,todo: ToDo){
            if (!todo.isNotificationEnable) return

            val componentName = ComponentName(context,JobSchedulerService::class.java)
            val jobLatency = todo.date.timeInMillis - System.currentTimeMillis()
            val bundle = PersistableBundle()
            bundle.putLong("id",todo.id)

            val jobInfo = JobInfo.Builder(todo.id.toInt(),componentName)
                .setMinimumLatency(jobLatency)
                .setOverrideDeadline(jobLatency + 30*1000)
                .setPersisted(true)
                .setExtras(bundle)
                .build()

            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE)
                                                as JobScheduler
            jobScheduler.schedule(jobInfo)
        }
        fun cancelScheduledTodo(context: Context,todo: ToDo){
            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE)
                    as JobScheduler
            jobScheduler.cancel(todo.id.toInt())
        }
    }
}