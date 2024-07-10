package com.example.speedy.data

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ToDoRepository : DefaultLifecycleObserver {
    fun addToDo(todo : String)
    fun removeToDo(todo : String)
    fun updateToDo(oldTodo: String, todo: String)
    fun getAllToDo() : MutableList<String>
}