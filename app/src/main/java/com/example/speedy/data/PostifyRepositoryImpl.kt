package com.example.speedy.data

import com.example.speedy.model.postify.Post
import com.example.speedy.retrofit.PostifyApi

// local store, file storage, api service

class PostifyRepositoryImpl(private val postifyApi: PostifyApi) : PostifyRepository {

    override suspend fun getOperation(): List<Post>? {
        return postifyApi.getPost().body()
    }

    override suspend fun postOperation(post: Post): Post? {
        return postifyApi.createPost(post = post).body()
    }

    override suspend fun deleteOperation(id : Int) : Post? {
       return  postifyApi.deletePost(id).body()
    }

    override suspend fun updateOperation() {
        TODO("Not yet implemented")
    }

    override suspend fun filterOperation(userId: Int): List<Post>? {
        return postifyApi.filterPosts(userId).body()
    }

}