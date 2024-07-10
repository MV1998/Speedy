package com.example.speedy.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.speedy.data.ToDoRepository

class NoteViewModelFactory(private val repositoryImpl: ToDoRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(repositoryImpl) as T
    }
}