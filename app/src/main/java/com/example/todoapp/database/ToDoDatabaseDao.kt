package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDatabaseDao {
    @Insert
    suspend fun insert(todo: ToDoEntity)
    @Update
    suspend fun update(todo: ToDoEntity)

    @Delete
    suspend fun delete(todo: ToDoEntity)

    @Query("SELECT * from task_table WHERE id = :key")
    suspend fun get(key: Long): ToDoEntity?

    @Query("SELECT * from task_table")
    suspend fun getAll(): List<ToDoEntity>?

    @Query("SELECT * from task_table WHERE date BETWEEN :startDate AND :EndDate ORDER BY date")
    suspend fun getBetweenDates(startDate: Long, EndDate: Long): List<ToDoEntity>?

    @Query("SELECT * from task_table ORDER BY id DESC LIMIT 1")
    suspend fun getLatest(): ToDoEntity?
}