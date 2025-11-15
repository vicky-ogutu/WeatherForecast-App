package com.example.weatherapp.models

import androidx.room.TypeConverter
import com.example.weatherapp.models.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromWeatherList(weather: List<Weather>?): String {
        return gson.toJson(weather)
    }

    @TypeConverter
    fun toWeatherList(weatherString: String): List<Weather>? {
        val type = object : TypeToken<List<Weather>>() {}.type
        return gson.fromJson(weatherString, type)
    }
}
