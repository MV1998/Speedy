package com.example.speedy.database.room_post

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.speedy.model.postify.Post

@Dao
interface PostDao {

    @Upsert
    suspend fun insert(post: Post)

    @Insert
    suspend fun insertAllPost(post: List<Post>)

    @Delete
    suspend fun delete(post: Post)

    @Query("DELETE FROM post WHERE id = :id")
    suspend fun deletePostById(id : Int) : Int

    @Query("SELECT * FROM post")
    suspend fun getAllPost() : List<Post>

    @Query("SELECT * FROM post WHERE userId = :userId")
    suspend fun getAllPostByUserId(userId: Int) : List<Post>

    @Query("SELECT * FROM post WHERE id = :id")
    suspend fun getPostById(id : Int) : Post

}