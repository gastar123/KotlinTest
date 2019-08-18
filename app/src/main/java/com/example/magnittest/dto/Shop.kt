package com.example.magnittest.dto

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
    var distance: Float)