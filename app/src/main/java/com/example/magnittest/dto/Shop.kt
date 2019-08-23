package com.example.magnittest.dto

import java.io.Serializable
import java.util.*

data class Shop(
    val id: Int,
    val type: Int,
    val name: String,
    val code: String,
    val address: String,
    val lng: Double,
    val lat: Double,
    val opening: String,
    val closing: String,
    val plastic: Boolean,
    val modification: String,
    var shopType: String,
    var status: String,
    var distance: Float,
    var image: Int
) : Serializable

data class Type(val id: Int, val name: String)

data class Sale(
    val id: Int,
    val type: Int,
    val name: String,
    val articleCategory: Int,
    val discountCategory: String,
    val image: String,
    val description: String,
    val startDate: Date,
    val endDate: Date,
    val unit: String,
    val price: Double,
    val oldPrice: Double,
    val priority: Int,
    val discountShopImage: String,
    val alcohol: Boolean,
    val publisher: String,
    val showDate: Date,
    val unit1: String,
    val barcode: String
) : Serializable

data class SalesType(val id: Int, val name: String)