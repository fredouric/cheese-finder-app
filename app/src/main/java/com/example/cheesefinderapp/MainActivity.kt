package com.example.cheesefinderapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val SERVER_BASE_URL = "https://cheese-epg-fts.cleverapps.io"
class MainActivity : AppCompatActivity() {

    private val cheeseCollection = CheeseCollection()
    private lateinit var cheeseService: CheeseService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SERVER_BASE_URL)
            .build()
        cheeseService = retrofit.create(CheeseService::class.java)
        cheeseService.getAllCheese()
            .enqueue(object : Callback<List<Cheese>> {
                override fun onResponse(
                    call: Call<List<Cheese>>,
                    response: Response<List<Cheese>>
                ){
                    response.body()?.forEach {
                        val cheese = Cheese(it.departement, it.fromage, it.lait, it.geo_point_2d)
                        cheeseCollection.addCheese(cheese)
                    }
                    val cheeseFragment = CheeseListFragment.newInstance(cheeseCollection.getArrayList())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.am_fragment_accueil, cheeseFragment)
                        .commit()
                }

                override fun onFailure(call: Call<List<Cheese>>, t: Throwable) {
                    // DO THINGS
                    t.printStackTrace()
                }
    })
    }
}