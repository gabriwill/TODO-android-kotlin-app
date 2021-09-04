package com.example.todoapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ToDo(
    val title: String,
    val description: String,
    val date: Calendar,
    val isNotificationEnable: Boolean,
    val id: Long = 0L
): Parcelable {
}