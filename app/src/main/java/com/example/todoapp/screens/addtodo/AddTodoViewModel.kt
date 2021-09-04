package com.example.todoapp.screens.addtodo

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.todoapp.R
import com.example.todoapp.model.ToDo
import com.example.todoapp.repository.RepositoryToDo
import kotlinx.coroutines.launch
import java.util.*

class AddTodoViewModel(private val repository:RepositoryToDo) : ViewModel() {

    fun createTodo(title: String,
                   description: String,
                   dateString: String,
                   hourString: String,
                   isNotificationEnable: Boolean, view: View
    ){

        val (day, month, year) = dateString.split("/").map {
            it.toInt()
        }

        val (hour, minute) = hourString.split(":").map {
            it.toInt()
        }
        val date = Calendar.getInstance()
        date.set(year,month-1,day,hour,minute)

        val todo = ToDo(title, description, date, isNotificationEnable)
        viewModelScope.launch {
            saveTodo(todo)
            view.findNavController().navigate(R.id.action_addTodo_to_mainScreen)
        }
    }

    private suspend fun saveTodo(todo: ToDo) {
        repository.addToDo(todo)
    }
}