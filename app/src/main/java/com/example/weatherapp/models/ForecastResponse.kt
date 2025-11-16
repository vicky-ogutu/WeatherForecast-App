package com.example.weatherapp.models

import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    val city: City,
    val list: List<ForecastItem>
)


