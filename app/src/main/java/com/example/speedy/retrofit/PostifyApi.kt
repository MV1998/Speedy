package com.example.speedy.retrofit

import com.example.speedy.model.postify.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PostifyApi {

    @GET("posts")
    suspend fun getPost() : Response<List<Post>>

    @POST("posts")
    suspend fun createPost(@Body post : Post) : Response<Post>

    @PUT("posts")
    suspend fun updatePost(@Body post: Post) : Response<Post>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id : Int) : Response<Post>

    @GET("posts")
    suspend fun filterPosts(@Query("userId") userId:Int) : Response<List<Post>>

}