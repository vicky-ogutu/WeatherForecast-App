package com.example.weatherapp.UserInterface

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class WeatherViewModel(
    private val repo: WeatherRepository
) : ViewModel() {

    private val _weatherList = MutableStateFlow<List<com.example.weatherapp.models.WeatherItem>>(emptyList())
    val weatherList: StateFlow<List<com.example.weatherapp.models.WeatherItem>> = _weatherList

    fun loadLocalWeather() {
        viewModelScope.launch {
            repo.getLocalWeather().collect { list ->
                _weatherList.value = list
            }
        }
    }

    fun refreshWeather(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            try {
                repo.fetchAndSaveWeather(lat, lon, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
