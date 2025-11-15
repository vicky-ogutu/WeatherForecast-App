package com.example.weatherapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherapp.models.WeatherTypeConverter

@Entity(tableName = "weather_table")
@TypeConverters(WeatherTypeConverter::class)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val city: String,
    val temperature: Double,
    val description: String
)

data class Weather(
    val main: String,
    val description: String
)

