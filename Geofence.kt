package com.example.ashishatte.location

import com.example.ashishatte.model.OfficeLocation
import kotlin.math.*

object Geofence {
    fun distanceMeters(aLat: Double, aLon: Double, bLat: Double, bLon: Double): Double {
        val earth = 6_371_000.0
        val dLat = Math.toRadians(bLat - aLat)
        val dLon = Math.toRadians(bLon - aLon)
        val a = sin(dLat / 2).pow(2) +
            cos(Math.toRadians(aLat)) * cos(Math.toRadians(bLat)) *
            sin(dLon / 2).pow(2)
        return 2 * earth * asin(sqrt(a))
    }

    fun inside(lat: Double, lon: Double, office: OfficeLocation): Boolean =
        distanceMeters(lat, lon, office.latitude, office.longitude) <= office.radiusMeters
}
