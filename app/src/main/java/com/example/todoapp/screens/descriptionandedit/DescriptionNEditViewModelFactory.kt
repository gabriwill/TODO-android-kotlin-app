package com.example.todoapp.screens.descriptionandedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.repository.RepositoryToDo

class DescriptionNEditViewModelFactory(private val repository: RepositoryToDo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DescriptionNEditViewModel::class.java)) {
            return DescriptionNEditViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}