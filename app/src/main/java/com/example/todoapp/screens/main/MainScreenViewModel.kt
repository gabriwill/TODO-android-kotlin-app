package com.example.todoapp.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.ToDo
import com.example.todoapp.utils.CalendarUtils
import java.util.*

class MainScreenViewModel : ViewModel() {

    private val _currentDate= MutableLiveData<Calendar>()
    val currentDate: LiveData<Calendar>
        get() = _currentDate

    private val _todoList= MutableLiveData<List<ToDo>>()
    val todoList: MutableLiveData<List<ToDo>>
        get() = _todoList

    init{
        _currentDate.value= Calendar.getInstance()
        _todoList.value = listOf<ToDo>(
            ToDo("gabriel","ksjdsjds\nsdhushdu","12:15","12/08/2021",false),
            ToDo("gabriel","ksjdsjds\nsdhushdu","12:15","12/08/2021",false),
        )
    }

    fun currentMonthYearString(): String {
        val month = CalendarUtils.getMonthString(currentDate.value?.get(Calendar.MONTH))
        val year = currentDate.value?.get(Calendar.YEAR)
        return "$month/$year"
    }

}