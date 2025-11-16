package com.example.weatherapp.models

import kotlinx.serialization.Serializable

@Serializable
data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<WeatherDescription>,
    val wind: Wind,
    val dt_txt: String,
    val rain: Rain? = null

)
