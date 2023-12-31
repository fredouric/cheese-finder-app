package com.example.cheesefinderapp.data.services

import com.example.cheesefinderapp.model.Cheese
import com.example.cheesefinderapp.model.pojo.UpdateCheeseRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface CheeseService {
    @GET("cheeses")
    fun getAllCheese(): Call<List<Cheese>>

    @GET("cheeses")
    fun getCheeseByID(@Query("id") id: String): Call<List<Cheese>>

    @GET("cheeses/search")
    fun searchCheese(@Query("fromage") fromage: String): Call<List<Cheese>>

    @PUT("cheeses")
    fun toggleFavorite(@Body updateCheeseRequest: UpdateCheeseRequest): Call<Cheese>

}