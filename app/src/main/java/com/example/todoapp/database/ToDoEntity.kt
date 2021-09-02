package com.example.todoapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "title")
    var title: String = "title",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "date")
    var date: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_notification_enable")
    var isNotificationEnable: Boolean = false,
) {
}