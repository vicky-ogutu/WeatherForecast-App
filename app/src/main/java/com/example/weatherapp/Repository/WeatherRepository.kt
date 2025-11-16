package com.example.weatherapp.Repository

import com.example.weatherapp.Api.WeatherApiService
import com.example.weatherapp.Dao.WeatherDao
import com.example.weatherapp.models.WeatherItem

    class WeatherRepository(
    private val api: WeatherApiService,
    private val dao: WeatherDao
) {
    fun getLocalWeather() = dao.getWeatherList()

    suspend fun fetchAndSaveWeather(lat: Double, lon: Double, apiKey: String) {
        val response = api.getWeather(lat, lon, apiKey)
        val items = response.list.map {
            WeatherItem(
                dt = it.dt,
                temp = it.temp,
                tempMin = it.tempMin,
                tempMax = it.tempMax,
                humidity = it.main.humidity,
                windSpeed = it.wind.speed,
                description = it.weather.firstOrNull()?.description ?: "",
                icon = it.weather.firstOrNull()?.icon ?: "",
                dt_txt = it.dt_txt
            )
        }
        dao.insertAll(items)
    }
}