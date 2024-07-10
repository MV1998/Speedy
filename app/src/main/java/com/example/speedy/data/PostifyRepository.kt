package com.example.speedy.data

import com.example.speedy.model.postify.Post

interface PostifyRepository {
    suspend fun getOperation() : List<Post>?
    suspend fun postOperation(post: Post) : Post?
    suspend fun deleteOperation(id : Int) : Post?
    suspend fun updateOperation()
    suspend fun filterOperation(userId : Int) : List<Post>?
}