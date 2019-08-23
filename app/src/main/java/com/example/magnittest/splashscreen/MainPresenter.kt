package com.example.magnittest.splashscreen

import android.content.Intent
import android.location.Location
import android.util.Log
import android.widget.Toast
import com.example.magnittest.Model
import com.example.magnittest.R
import com.example.magnittest.shops.MainActivity

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
                when (shop.type) {
                    1 -> shop.image = R.drawable.magnit_magnit
                    2 -> shop.image = R.drawable.magnit_hiper
                    3 -> shop.image = R.drawable.magnit_cosmetic
                    4 -> shop.image = R.drawable.magnit_pharmacy
                    5 -> shop.image = R.drawable.magnit_wholesale
                }
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

    fun setToast() {
        Toast.makeText(splashActivity, "Нет подключения к серверу", Toast.LENGTH_SHORT).show()
    }
}