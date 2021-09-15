package com.example.todoapp.repository

import android.content.Context
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.database.ToDoDatabaseDao
import com.example.todoapp.database.ToDoEntity
import com.example.todoapp.model.ToDo
import java.util.*

class DatabaseImpl(val context: Context): RepositoryToDo {

    private val databaseDao: ToDoDatabaseDao =
        ToDoDatabase.getInstance(context).todoDatabaseDao

    override suspend fun getToDoByDay(date: Calendar): List<ToDo>? {
        val _date = date.clone() as Calendar
        val year = _date.get(Calendar.YEAR)
        val month = _date.get(Calendar.MONTH)
        val day = _date.get(Calendar.DAY_OF_MONTH)

        _date.set(year,month,day,0,0)
        val startOfDay = _date.timeInMillis
        _date.set(year,month,day,23,59)
        val endOfDay = _date.timeInMillis
        val listOfRows = databaseDao.getBetweenDates(startOfDay,endOfDay)
        val listOfTodos = mutableListOf<ToDo>()
        if(listOfRows != null) {
            listOfRows.forEach { data ->
                val dataDate = Calendar.getInstance()
                dataDate.timeInMillis = data.date
                listOfTodos.add(ToDo(
                    data.title,
                    data.description,
                    dataDate,
                    data.isNotificationEnable,
                    id = data.id
                ))
            }
            return listOfTodos
        }else return null
    }

    override suspend fun addToDo(toDo: ToDo): Boolean {
        val row = ToDoEntity(0,
            toDo.title,
            toDo.description,
            toDo.date.timeInMillis,
            toDo.isNotificationEnable
        )

        databaseDao.insert(row)
        val newTodo = databaseDao.getLatest()
        if(newTodo != null) {
            val dataDate = Calendar.getInstance()
            dataDate.timeInMillis = newTodo.date
            TodoNotificationScheduler.scheduleTodo(
                context, ToDo(
                    newTodo.title,
                    newTodo.description,
                    dataDate,
                    newTodo.isNotificationEnable,
                    id = newTodo.id
                )
            )
        }
        return true
    }

    override suspend fun updateToDo(toDo: ToDo): Boolean {
        val row = ToDoEntity(toDo.id,
            toDo.title,
            toDo.description,
            toDo.date.timeInMillis,
            toDo.isNotificationEnable
        )

        databaseDao.update(row)
        TodoNotificationScheduler.scheduleTodo(context,toDo)
        return true
    }

    override suspend fun deleteToDo(toDo: ToDo): Boolean {
        val row = ToDoEntity(toDo.id,
            toDo.title,
            toDo.description,
            toDo.date.timeInMillis,
            toDo.isNotificationEnable
        )

        databaseDao.delete(row)
        TodoNotificationScheduler.cancelScheduledTodo(context,toDo)
        return true
    }

    suspend fun getToDoByid(id: Long): ToDo?{
        val data = databaseDao.get(id)
        return if(data != null){
            val dataDate = Calendar.getInstance()
            dataDate.timeInMillis = data.date
            ToDo(
                data.title,
                data.description,
                dataDate,
                data.isNotificationEnable,
                id = data.id
            )
        } else null
    }
}