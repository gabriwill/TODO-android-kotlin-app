package com.example.todoapp.screens.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.ToDo
import com.example.todoapp.utils.CalendarUtils
import com.example.todoapp.utils.CalendarUtils.Companion.getDaysList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
            ToDo("gabriel","ksjdsjds\nsdhushdu",currentDate.value!!,false),
            ToDo("gabriel","ksjdsjds\nsdhushdu",currentDate.value!!,false),
        )
    }

    fun currentMonthYearString(): String {
        val month = currentDate.value
            ?.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale("pt","BR"))
            ?.replaceFirstChar { it.uppercase() }
        val year = currentDate.value?.get(Calendar.YEAR)
        return "$month/$year"
    }

    fun goToNextMonth(){
        val date: Calendar? = _currentDate.value
        if(currentDate.value?.get(Calendar.MONTH)==11) date?.roll(Calendar.YEAR,true)
        date?.roll(Calendar.MONTH,true)
        _currentDate.value = date?:_currentDate.value
    }

    fun goToPreviousMonth(){
        val date: Calendar? = _currentDate.value
        if(currentDate.value?.get(Calendar.MONTH)==0) date?.roll(Calendar.YEAR,false)
        date?.roll(Calendar.MONTH,false)
        _currentDate.value = date?:_currentDate.value
    }

}