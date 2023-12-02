package com.example.cheesefinderapp.ui

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.model.Cheese
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class InfoCheeseActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMapView: MapView
    private lateinit var cheese: Cheese
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_cheese)

        cheese = intent.getSerializableExtra("cheese") as Cheese

        setInfoCheese(cheese)

        mMapView = findViewById(R.id.aic_map)
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync(OnMapReadyCallback { googleMap ->
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            onMapReady(googleMap)
        })
    }

    private fun setInfoCheese(cheese: Cheese) {
        val cheeseNameTextView: TextView = findViewById(R.id.aic_cheese_name)
        val cheeseLaitTextView: TextView = findViewById(R.id.aic_cheese_milk)
        val cheeseDepartementTextView: TextView = findViewById(R.id.aic_cheese_department)

        cheeseNameTextView.text = cheese.fromage
        cheeseLaitTextView.text = "Lait : " + cheese.lait?.joinToString(", ")
        cheeseDepartementTextView.text = "Département : " + cheese.departement
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.go_back_menu, menu)
        val backBtn = menu.findItem(R.id.app_bar_back)

        backBtn.setOnMenuItemClickListener {
            onBackPressed()
            true
        }
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val coordinates = LatLng(cheese.geo_point_2d.lat, cheese.geo_point_2d.lon)
        googleMap.addMarker(MarkerOptions().position(coordinates).title(cheese.fromage))
        googleMap.moveCamera(CameraUpdateFactory.zoomBy(5f))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates))
    }
}
