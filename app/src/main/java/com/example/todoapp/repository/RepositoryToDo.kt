package com.example.todoapp.repository

import com.example.todoapp.model.ToDo
import java.util.*

interface RepositoryToDo {
    fun getToDoByDate(date:Calendar):List<ToDo>
    fun addToDo(toDo: ToDo): Boolean
    fun deleteToDo(toDo: ToDo): Boolean
}