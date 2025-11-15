package com.example.weatherapp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherapp.models.WeatherTypeConverter

@Entity(tableName = "weather_forecast")
data class WeatherForecast(
    @PrimaryKey val dt: Long,
    @Embedded val main: MainWeatherData,
    @Embedded(prefix = "wind_") val wind: Wind,
    @Embedded(prefix = "clouds_") val clouds: Clouds,
    @TypeConverters(WeatherTypeConverter::class)
    val weather: List<WeatherDescription>,
    val dtTxt: String,
    @Embedded(prefix = "rain_") val rain: Rain? = null,
    @Embedded(prefix = "sys_") val sys: Sys? = null
)
