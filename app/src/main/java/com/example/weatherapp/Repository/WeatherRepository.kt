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
        val cityName = response.city.name

        val items = response.list.map { it ->
            WeatherItem(
                dt = it.dt,
                temp = it.main.temp,
                tempMin = it.main.temp_min,
                tempMax = it.main.temp_max,
                humidity = it.main.humidity,
                windSpeed = it.wind.speed,
                rainVolume = it.rain?.volume ?: 0.0,
                description = it.weather.firstOrNull()?.description?.capitalize() ?: "No description",
                icon = it.weather.firstOrNull()?.icon ?: "",
                dt_txt = it.dt_txt,
                city = cityName
            )
        }

        dao.insertAll(items)
    }
}