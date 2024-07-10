package com.example.speedy.retrofit

import com.example.speedy.model.Data
import com.example.speedy.model.breed.Breeds
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// spend time on get and post apis.

/*
Annotations:
Retrofit provides with a list of annotations for each of the HTTP methods: @GET, @POST, @PUT, @DELETE, @PATCH or @HEAD, @Header, @Headers, @HeaderMap
 */
interface FactsApis {

    @Headers("Content-Type: application/json")
    @GET("facts")
    suspend fun getFacts() : Response<Data>

    @Headers("Content-Type: application/json")
    @GET("breeds")
    suspend fun breeds(@Query("page[number]") page : Int) : Response<Breeds>

}