package com.example.weatherapp.models

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)

