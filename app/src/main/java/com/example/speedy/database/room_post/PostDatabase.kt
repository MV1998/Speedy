package com.example.speedy.database.room_post

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.speedy.model.postify.Post

@Database(entities = [Post::class], version = 1)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: PostDatabase? = null

        @Synchronized
        fun getPostDatabase(context: Context): PostDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, PostDatabase::class.java, "post_db")
                    .enableMultiInstanceInvalidation().build()
            }
            return INSTANCE!!
        }
    }

}