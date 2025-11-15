package com.example.weatherapp.models

data class WeatherResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherForecast>,
    val city: City? = null // Optional
)
