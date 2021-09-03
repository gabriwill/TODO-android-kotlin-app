package com.example.todoapp.screens.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.ToDo
import com.example.todoapp.repository.RepositoryToDo
import com.example.todoapp.utils.CalendarUtils
import com.example.todoapp.utils.CalendarUtils.Companion.getDaysList
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainScreenViewModel(private val repository: RepositoryToDo) : ViewModel() {

    private val _currentDate= MutableLiveData<Calendar>()
    val currentDate: LiveData<Calendar>
        get() = _currentDate

    private val _todoList= MutableLiveData<List<ToDo>>()
    val todoList: MutableLiveData<List<ToDo>>
        get() = _todoList

    init{
        _currentDate.value= Calendar.getInstance()
        getTodoList()
    }

    fun getTodoList(){
        viewModelScope.launch {
            _todoList.value = repository.getToDoByDay(currentDate.value?:Calendar.getInstance())
        }
    }

    fun currentMonthYearString(): String {
        val month = currentDate.value
            ?.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale("pt","BR"))
            ?.replaceFirstChar { it.uppercase() }
        val year = currentDate.value?.get(Calendar.YEAR)
        return "$month/$year"
    }

    fun setCurrentDay(day: Int){
        if(day>0) _currentDate.value?.set(Calendar.DAY_OF_MONTH,day)
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