package com.example.magnittest.dto

import java.io.Serializable

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
    var distance: Float
) : Serializable

data class Type(val id: Int, val name: String)