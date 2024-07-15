package com.example.speedy.model.postify

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity()
data class Post(
    val title : String,
    val body : String,
    val userId : Int,
    @PrimaryKey val id: Int
)