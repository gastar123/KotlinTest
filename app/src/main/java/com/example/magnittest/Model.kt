package com.example.magnittest

import android.util.Log
import com.example.magnittest.dto.Shops
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class Model {

    val magnitApi = MagnitApi.create()
    fun getShops() {
        magnitApi.getShops()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ results ->
                results.forEach {
                    Log.e("Result", it.toString())
                }},
                { error -> Log.e("ERROR", error.message, error) })
    }
}