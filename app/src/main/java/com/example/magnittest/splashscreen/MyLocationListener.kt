package com.example.magnittest.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import io.reactivex.functions.Consumer

class MyLocationListener(context: Context, presenter: MainPresenter) : LocationListener {

    var imHere: Location? = null
    var consumer: Consumer<Location>? = null
    var locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    var presenter = presenter

    @SuppressLint("MissingPermission")
    fun setUpLocationListener(consumer: Consumer<Location>) {
        this.consumer = consumer
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, this)
            imHere = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
            imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
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

    @SuppressLint("MissingPermission")
    override fun onProviderEnabled(provider: String?) {
        var location = locationManager.getLastKnownLocation(provider)
        if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            consumer?.accept(location)
        }
    }

    override fun onProviderDisabled(provider: String?) {
    }
}