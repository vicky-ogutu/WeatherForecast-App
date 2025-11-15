package com.example.weatherapp.models

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val list: List<ForecastItem>
)
