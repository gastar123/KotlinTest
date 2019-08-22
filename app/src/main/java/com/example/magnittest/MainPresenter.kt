package com.example.magnittest

import android.content.Intent
import android.location.Location
import android.util.Log

class MainPresenter(private val splashActivity: SplashActivity) {

    fun getTypes() {
        Model.getTypes(this)
    }

    fun checkShopList(myLocation: Location?) {
        Model.myLocation = myLocation
        if (Model.shopList.isNotEmpty() && myLocation != null) {
            var distance = FloatArray(1)
            var sortedList = Model.shopList.map { shop ->
                Location.distanceBetween(myLocation.latitude, myLocation.longitude, shop.lat, shop.lng, distance)
                shop.distance = distance[0]
                shop
            }.sortedBy { it.distance }
            sortedList.subList(0, 100).forEach { Log.e("SHOP", it.toString()) }
            Model.shopList = sortedList
            toNextScreen()
        }
    }

    private fun toNextScreen() {
        val intent = Intent(splashActivity, MainActivity::class.java)
        splashActivity.startActivity(intent)
        splashActivity.finish()
    }
}