package com.example.speedy.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FactsService {

    private fun getRetrofitBuilder() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dogapi.dog/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getFactApi() : FactsApis {
        return getRetrofitBuilder().create(FactsApis::class.java)
    }
}