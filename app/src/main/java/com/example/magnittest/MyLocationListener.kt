package com.example.magnittest

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import io.reactivex.functions.Consumer

class MyLocationListener(context: Context) : LocationListener {

    var imHere: Location? = null
    var consumer: Consumer<Location>? = null
    var locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    fun setUpLocationListener(consumer: Consumer<Location>) {
        this.consumer = consumer
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }

    fun stop() {
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location?) {
        imHere = location
        consumer?.accept(imHere)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }
}