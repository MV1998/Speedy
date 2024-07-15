package com.example.speedy.data

import android.content.Context
import android.util.Log
import com.example.speedy.database.room_post.PostDao
import com.example.speedy.model.postify.Post
import com.example.speedy.retrofit.PostifyApi
import com.example.speedy.utilities.InternetConnectivity
import com.google.gson.Gson

// local store, file storage, api service

class PostifyRepositoryImpl(private val context : Context,
                            private val postifyApi: PostifyApi,
                            private val postDao: PostDao)
    : PostifyRepository {

    override suspend fun getOperation(): List<Post> {
        if (InternetConnectivity.isConnected(context)) {
            postifyApi.getPost().body()?.let {
                for (post in it) {
                    postDao.insert(post)
                }
            }
        }
        return postDao.getAllPost()
    }

    override suspend fun postOperation(post: Post): Post {
        if (InternetConnectivity.isConnected(context)) {
            postifyApi.createPost(post = post).body()
        }
        postDao.insert(post)
        return postDao.getPostById(post.id)
    }

    override suspend fun deleteOperation(id : Int) : Int {
        if (InternetConnectivity.isConnected(context)) {
            postifyApi.deletePost(id).body()
        }
        return postDao.deletePostById(id)
    }

    override suspend fun updateOperation() {
        TODO("Not yet implemented")
    }

    override suspend fun filterOperation(userId: Int): List<Post>? {
        if (InternetConnectivity.isConnected(context)) {
            return postifyApi.filterPosts(userId).body()
        }
        return postDao.getAllPostByUserId(userId)
    }

}