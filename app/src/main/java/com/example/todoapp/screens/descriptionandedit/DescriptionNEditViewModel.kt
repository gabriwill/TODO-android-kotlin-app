package com.example.todoapp.screens.descriptionandedit

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.todoapp.R
import com.example.todoapp.model.ToDo
import com.example.todoapp.repository.RepositoryToDo
import com.example.todoapp.utils.CalendarUtils.Companion.getDateFromStrings
import kotlinx.coroutines.launch
import java.util.*

class DescriptionNEditViewModel(private val repository: RepositoryToDo) : ViewModel() {
    private val _screenMode = MutableLiveData<Int>()
    val screenMode: LiveData<Int>
        get() = _screenMode

    init {
        changeToDescriptionMode()
    }

    fun changeToEditMode(){
        _screenMode.value = DescriptionNEditFragment.EDIT_MODE
    }
    fun changeToDescriptionMode(){
        _screenMode.value = DescriptionNEditFragment.DESCRIPTION_MODE
    }

    fun updateTodo(id: Long,
                   title: String,
                   description: String,
                   dateString: String,
                   hourString: String,
                   isNotificationEnable: Boolean, view: View
    ){
        val date = getDateFromStrings(dateString,hourString)

        val todo = ToDo(title, description, date, isNotificationEnable,id)
        viewModelScope.launch {
            repository.updateToDo(todo)
            view.findNavController().popBackStack()
        }
    }
}