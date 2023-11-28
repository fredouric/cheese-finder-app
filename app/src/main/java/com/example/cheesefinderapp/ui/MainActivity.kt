package com.example.cheesefinderapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.data.services.CheeseService
import com.example.cheesefinderapp.model.Cheese
import com.example.cheesefinderapp.model.CheeseCollection
import com.example.cheesefinderapp.ui.about.AboutFragment
import com.example.cheesefinderapp.ui.cheeselist.CheeseListFragment
import com.example.cheesefinderapp.ui.maps.MapsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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
                ) {
                    response.body()?.forEach {
                        val cheese = Cheese(it.departement, it.fromage, it.lait, it.geo_point_2d)
                        cheeseCollection.addCheese(cheese)
                    }
                    loadFragment(CheeseListFragment.newInstance(cheeseCollection.getArrayList()))
                }

                override fun onFailure(call: Call<List<Cheese>>, t: Throwable) {
                    t.printStackTrace()
                }
            })

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.am_navigation_bar)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.a_menu_item_home -> {
                    loadFragment(CheeseListFragment.newInstance(cheeseCollection.getArrayList()))
                    true
                }

                R.id.a_menu_item_map -> {
                    loadFragment(MapsFragment())
                    true
                }

                R.id.a_menu_item_about -> {
                    loadFragment(AboutFragment.newInstance())
                    true
                }

                else -> false
            }

        }
    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.am_fragment_accueil, fragment)
            .commit()
    }

}