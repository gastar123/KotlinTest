package com.example.magnittest

import android.location.Location
import android.util.Log
import com.example.magnittest.dto.Shop
import com.example.magnittest.dto.Type
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

object Model {

    var shopList: List<Shop> = ArrayList()
    var typeList: List<Type> = ArrayList()
    var myLocation: Location? = null

    private val magnitApi = MagnitApi.create()

    fun getTypes(mainPresenter: MainPresenter) {
        magnitApi.getTypes()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ typeList ->
                Log.e("SERVER", "ПОЛУЧИЛ ТИПЫ")
                this.typeList = typeList
                getShops(mainPresenter)
            },
                { error -> Log.e("ERROR", error.message, error) })
    }

    private fun getShops(mainPresenter: MainPresenter) {
        magnitApi.getShops()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ shopList ->
                Log.e("SERVER", "ПОЛУЧИЛ ДАННЫЕ")
                val filteredShops = shopList.filter { shop -> shop.type != 0 }.map { shop ->
                    when (shop.type) {
                        1 -> shop.shopType = "Магазин Магнит"
                        2 -> shop.shopType = "Магнит Семейный"
                        3 -> shop.shopType = "Магнит Косметик"
                        4 -> shop.shopType = "Магнит Аптека"
                        5 -> shop.shopType = "Магнит-Опт"
                        else -> shop.shopType = "unknown"
                    }
                    shop
                }
                this.shopList = filteredShops
                mainPresenter.checkShopList(myLocation)
            },
                { error -> Log.e("ERROR", error.message, error) })
    }

    fun getSales(shopId: Int, salesActivity: SalesActivity) {
        magnitApi.getSales(shopId, "МПМ")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ salesActivity.setSaleList(it)
                Log.e("SERVER", "ПОЛУЧИЛ АКЦИИ") },
                { error -> Log.e("ERROR", error.message, error) })
    }


}