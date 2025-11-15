package com.example.weatherapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "weather_table")
data class WeatherItem(
    @PrimaryKey val dt: Long,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String,
    val dt_txt: String
)
