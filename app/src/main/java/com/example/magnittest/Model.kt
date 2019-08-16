package com.example.magnittest

import android.util.Log
import com.example.magnittest.dto.Shop
import io.reactivex.android.schedulers.AndroidSchedulers

class Model(val mainActivity: MainActivity) {

    val magnitApi = MagnitApi.create()
    fun getShops() {
        magnitApi.getShops()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ shops ->
                val filteredShops = shops.filter { shop -> shop.type != 0 }
                mainActivity.updateView(filteredShops)
            },
                { error -> Log.e("ERROR", error.message, error) })
    }
}