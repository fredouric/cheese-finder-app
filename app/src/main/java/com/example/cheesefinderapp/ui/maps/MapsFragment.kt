package com.example.cheesefinderapp.ui.maps

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


private const val ARG_CHEESE_COLLECTION = "param_cheese_collection"

class MapsFragment : Fragment() {

    private var cheeseCollection: ArrayList<Cheese>? = null

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        cheeseCollection?.forEach {
            val coordinates = LatLng(it.geo_point_2d.lat, it.geo_point_2d.lon)
            googleMap.addMarker(MarkerOptions().position(coordinates).title(it.fromage))
        }
        val centreFrance = LatLng(46.606111, 1.875278)
        googleMap.moveCamera(CameraUpdateFactory.zoomBy(3.25f))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(centreFrance))

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
