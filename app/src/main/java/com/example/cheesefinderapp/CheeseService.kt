package com.example.cheesefinderapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CheeseService {
    @GET("cheeses")
    fun getAllCheese(): Call<List<Cheese>>

    @POST("cheeses")
    fun addCheese(@Body cheese: Cheese): Call<Cheese>
}