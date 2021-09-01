package com.example.todoapp.model

data class ToDo(
    val title: String,
    val description: String,
    val hour: String,
    val date: String,
    val isNotificationEnable: Boolean
) {
}