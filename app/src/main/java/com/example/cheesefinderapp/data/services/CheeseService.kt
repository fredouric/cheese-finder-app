package com.example.cheesefinderapp.data.services

import com.example.cheesefinderapp.model.Cheese
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CheeseService {
    @GET("cheeses")
    fun getAllCheese(): Call<List<Cheese>>

    @GET("cheeses")
    fun getCheeseByID(@Query("id") id: String): Call<List<Cheese>>


    @POST("cheeses")
    fun addCheese(@Body cheese: Cheese): Call<Cheese>

}