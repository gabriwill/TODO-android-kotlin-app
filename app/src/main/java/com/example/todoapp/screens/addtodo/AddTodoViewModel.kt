package com.example.todoapp.screens.addtodo

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.todoapp.R
import com.example.todoapp.model.ToDo
import com.example.todoapp.repository.RepositoryToDo
import com.example.todoapp.utils.CalendarUtils
import kotlinx.coroutines.launch
import java.util.*

class AddTodoViewModel(private val repository:RepositoryToDo) : ViewModel() {

    fun createTodo(title: String,
                   description: String,
                   dateString: String,
                   hourString: String,
                   isNotificationEnable: Boolean, view: View
    ){

        val date = CalendarUtils.getDateFromStrings(dateString, hourString)

        val todo = ToDo(title, description, date, isNotificationEnable)
        viewModelScope.launch {
            saveTodo(todo)
            view.findNavController().popBackStack()
        }
    }

    private suspend fun saveTodo(todo: ToDo) {
        repository.addToDo(todo)
    }
}