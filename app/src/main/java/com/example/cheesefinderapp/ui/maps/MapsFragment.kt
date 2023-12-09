package com.example.cheesefinderapp.ui.maps

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cheesefinderapp.R
import com.example.cheesefinderapp.model.Cheese
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.transition.MaterialFadeThrough
import com.google.gson.Gson
import com.google.maps.android.data.geojson.GeoJsonLayer
import org.json.JSONObject


private const val ARG_CHEESE_COLLECTION = "param_cheese_collection"

class MapsFragment : Fragment() {

    private var cheeseCollection: ArrayList<Cheese>? = null

    private val callback = OnMapReadyCallback { googleMap ->

        val departmentLayers = HashMap<String, GeoJsonLayer>()
        val departmentMarkerCoordinates = HashMap<String, LatLng>()
        val gson = Gson()

        cheeseCollection?.forEach {
            val jsonString = gson.toJson(it.geo_shape)
            val layer = GeoJsonLayer(googleMap, JSONObject(jsonString))
            departmentLayers[it.departement] = layer
            departmentMarkerCoordinates[it.departement] =
                LatLng(it.geo_point_2d.lat, it.geo_point_2d.lon)
        }

        departmentLayers.forEach { (departmentName, layer) ->
            departmentMarkerCoordinates[departmentName]?.let {
                MarkerOptions().position(it)
                    .title(departmentName)
                    .snippet(showCheesesForDepartment(departmentName))
            }?.let {
                googleMap.addMarker(
                    it
                )
            }

            val layerStyle = layer.defaultPolygonStyle
            layerStyle.strokeColor = Color.RED
            layerStyle.strokeWidth = 2.5f
            layer.addLayerToMap()
        }

        val centreFrance = LatLng(46.606111, 1.875278)
        googleMap.moveCamera(CameraUpdateFactory.zoomBy(3.25f))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(centreFrance))
    }

    private fun showCheesesForDepartment(departmentName: String): String? {
        val cheesesInDepartment = cheeseCollection?.filter { it.departement == departmentName }
        return cheesesInDepartment?.joinToString(", ") { it.fromage }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            cheeseCollection =
                arguments?.getSerializable(ARG_CHEESE_COLLECTION) as ArrayList<Cheese>
        }
        enterTransition = MaterialFadeThrough()
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    companion object {
        @JvmStatic
        fun newInstance(cheeseCollection: ArrayList<Cheese>) =
            MapsFragment().apply {
                arguments =
                    Bundle().apply { putSerializable(ARG_CHEESE_COLLECTION, cheeseCollection) }
            }
    }
}
