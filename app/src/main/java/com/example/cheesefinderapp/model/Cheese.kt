package com.example.cheesefinderapp.model

import java.io.Serializable

data class Cheese(val departement: String, val fromage: String, val lait: List<String>, val geo_point_2d: GeoPoint2D) : Serializable
