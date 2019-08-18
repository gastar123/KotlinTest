package com.example.magnittest

import android.location.Location
import android.util.Log
import com.example.magnittest.dto.Shop
import io.reactivex.android.schedulers.AndroidSchedulers

class Model(private val mainActivity: MainActivity) {

    var shopList: List<Shop> = ArrayList()
    var myLocation: Location? = null

    private val magnitApi = MagnitApi.create()
    fun getShops() {
        magnitApi.getShops()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ shopList ->
                val filteredShops = shopList.filter { shop -> shop.type != 0 }
                this.shopList = filteredShops
                checkShopList(myLocation)
            },
                { error -> Log.e("ERROR", error.message, error) })
    }

    fun checkShopList(myLocation: Location?) {
        this.myLocation = myLocation
        if (shopList.isNotEmpty() && myLocation != null) {
            var distance = FloatArray(1)
            var sortedList = shopList.map { shop ->
                Location.distanceBetween(myLocation.latitude, myLocation.longitude, shop.lat, shop.lng, distance)
                shop.distance = distance[0]
                shop
            }.sortedBy { it.distance }
            sortedList.subList(0, 100).forEach { Log.e("SHOP", it.toString()) }
            mainActivity.updateView(sortedList)
        }
    }
}