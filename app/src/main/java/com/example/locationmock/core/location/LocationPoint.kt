package com.example.locationmock.core.location

data class LocationPoint(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double = 0.0,
    val accuracy: Float = 5f
)
