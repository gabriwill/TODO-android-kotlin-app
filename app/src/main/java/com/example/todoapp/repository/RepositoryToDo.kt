package com.example.todoapp.repository

import com.example.todoapp.model.ToDo
import java.util.*

interface RepositoryToDo {
    suspend fun getToDoByDay(date:Calendar):List<ToDo>?
    suspend fun addToDo(toDo: ToDo): Boolean
    suspend fun updateToDo(toDo: ToDo): Boolean
    suspend fun deleteToDo(toDo: ToDo): Boolean
}