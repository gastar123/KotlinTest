package com.example.magnittest

import android.location.Location
import android.util.Log
import com.example.magnittest.dto.Shop
import com.example.magnittest.dto.Type
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class Model(private val mainActivity: MainActivity) {

    var shopList: List<Shop> = ArrayList()
    var typeList: List<Type> = ArrayList()
    var myLocation: Location? = null

    private val magnitApi = MagnitApi.create()

    fun getTypes() {
        magnitApi.getTypes()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ typeList ->
                Log.e("SERVER", "ПОЛУЧИЛ ТИПЫ")
                this.typeList = typeList
                getShops()
            },
                { error -> Log.e("ERROR", error.message, error) })
    }

    private fun getShops() {
        magnitApi.getShops()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ shopList ->
                Log.e("SERVER", "ПОЛУЧИЛ ДАННЫЕ")
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