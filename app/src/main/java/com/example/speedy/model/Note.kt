package com.example.speedy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("note")
data class Note(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    var title: String,
    var description: String
)