package com.example.speedy.data

import com.example.speedy.database.LocalPairedDB
import com.example.speedy.database.room_post.NoteDao
import com.example.speedy.model.Note


// we give the cloud, local room, shared preference, file path in repository.

class InMemoryToDoRepositoryImpl(private val noteDao : NoteDao): ToDoRepository {

    override suspend fun addToDo(todo: Note) {
        noteDao.add(todo)
    }

    override suspend fun removeToDo(todo: Note) {
        noteDao.delete(todo)
    }

    override suspend fun updateToDo(todo: Note) {
        noteDao.update(todo)
    }

    override suspend fun getAllToDo(): List<Note> {
        return noteDao.getAllNotes()
    }

}