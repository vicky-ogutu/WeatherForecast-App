package com.example.weatherapp.models

import kotlinx.serialization.Serializable

@Serializable
data class ForecastItem(
    val dt: Long,
    val main: Main,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val weather: List<WeatherDescription>,
    val wind: Wind,
    val dt_txt: String,

)

