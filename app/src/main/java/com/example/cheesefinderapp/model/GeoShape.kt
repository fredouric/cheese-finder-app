package com.example.cheesefinderapp.model

import java.io.Serializable

data class GeoShape(val type: String, val geometry: Geometry, val properties: Properties) :
    Serializable
