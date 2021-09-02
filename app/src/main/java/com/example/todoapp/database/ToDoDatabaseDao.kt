package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoDatabaseDao {
    @Insert
    fun insert(todo: ToDoEntity)
    @Update
    fun update(todo: ToDoEntity)

    @Query("SELECT * from task_table WHERE id = :key")
    fun get(key: Long): ToDoEntity?

    @Query("SELECT * from task_table")
    fun getAll(): List<ToDoEntity>?

    @Query("SELECT * from task_table WHERE date BETWEEN :startDate AND :EndDate ORDER BY date")
    fun getBetweenDates(startDate: Long, EndDate: Long): List<ToDoEntity>?
}