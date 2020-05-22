package com.example.trailgage.backend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://trailgage.local/"
    val instance: Endpoint by lazy {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        retrofit.create(Endpoint::class.java)


    }

}