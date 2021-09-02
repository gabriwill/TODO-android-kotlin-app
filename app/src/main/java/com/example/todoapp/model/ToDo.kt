package com.example.todoapp.model

import java.util.*

data class ToDo(
    val title: String,
    val description: String,
    val date: Calendar,
    val isNotificationEnable: Boolean,
    val id: Long = 0L
) {
}