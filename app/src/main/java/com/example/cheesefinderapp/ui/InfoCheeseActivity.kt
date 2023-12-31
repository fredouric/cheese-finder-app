package com.example.cheesefinderapp.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.data.services.CheeseService
import com.example.cheesefinderapp.model.Cheese
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.maps.android.data.geojson.GeoJsonLayer
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class InfoCheeseActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMapView: MapView
    private lateinit var cheese: Cheese
    private lateinit var cheeseService: CheeseService
    private var customMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_cheese)

        val cheeseID = intent.getStringExtra("cheeseID") as String

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SERVER_BASE_URL)
            .build()
        cheeseService = retrofit.create(CheeseService::class.java)
        cheeseService.getCheeseByID(cheeseID)
            .enqueue(object : Callback<List<Cheese>> {
                override fun onResponse(
                    call: Call<List<Cheese>>,
                    response: Response<List<Cheese>>
                ) {
                    val cheeses = response.body()

                    if (!cheeses.isNullOrEmpty()) {
                        cheese = cheeses[0]
                        setInfoCheese(cheese)

                    }
                    mMapView = findViewById(R.id.aic_map)
                    mMapView.onCreate(savedInstanceState)
                    mMapView.getMapAsync(OnMapReadyCallback { googleMap ->
                        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                        onMapReady(googleMap)
                    })
                }

                override fun onFailure(call: Call<List<Cheese>>, t: Throwable) {
                    t.printStackTrace()
                }
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

        //resize cheese marker
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.cheese)
        val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 100, 100, false);
        val resizedMarkerIcon = BitmapDescriptorFactory.fromBitmap(resizedBitmap);

        //assign cheese marker to coordinates and resizedMarkerIcon
        customMarker = googleMap.addMarker(
            MarkerOptions()
                .position(coordinates)
                .icon(resizedMarkerIcon)
        )

        googleMap.moveCamera(CameraUpdateFactory.zoomBy(5f))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates))


        val gson = Gson()
        val jsonString = gson.toJson(cheese.geo_shape)
        val layer = GeoJsonLayer(googleMap, JSONObject(jsonString))

        val polygonStyle = layer.defaultPolygonStyle
        polygonStyle.fillColor = Color.argb(80, 254, 229, 156)

        layer.addLayerToMap()
    }
}
