package com.example.speedy.data

import com.example.speedy.database.LocalPairedDB


// we give the cloud, local room, shared preference, file path in repository.

class InMemoryToDoRepositoryImpl(private val dataStore: LocalPairedDB): ToDoRepository {

    override fun addToDo(todo: String) {
        dataStore.todos.add(todo)
    }

    override fun removeToDo(todo: String) {
        dataStore.todos.remove(todo)
    }

    override fun updateToDo(oldTodo: String, todo: String) {
        dataStore.todos[dataStore.todos.indexOf(oldTodo)] = todo
    }

    override fun getAllToDo(): MutableList<String> {
        return dataStore.todos
    }

}