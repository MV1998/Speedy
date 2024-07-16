package com.example.speedy.database.room_post

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.speedy.model.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun add(note: Note)

    @Delete
    suspend fun delete(note: Note) : Int

    @Query("SELECT * FROM note")
    suspend fun getAllNotes() : List<Note>

    @Update
    suspend fun update(note: Note) : Int

    @Query("UPDATE note SET title = :title, description = :description WHERE id = :id")
    suspend fun updateNoteById(id : Int, title : String, description : String) : Int
}