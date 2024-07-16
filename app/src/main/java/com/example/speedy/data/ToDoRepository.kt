package com.example.speedy.data

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.speedy.model.Note

interface ToDoRepository {
    suspend fun addToDo(todo : Note)
    suspend fun removeToDo(todo : Note)
    suspend fun updateToDo(todo: Note)
    suspend fun getAllToDo() : List<Note>
}