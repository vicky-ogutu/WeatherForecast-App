package com.example.weatherapp.UserInterface

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.models.WeatherItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repo: WeatherRepository
) : ViewModel() {

    private val _weatherList = MutableStateFlow<List<WeatherItem>>(emptyList())
    val weatherList: StateFlow<List<WeatherItem>> = _weatherList

    val currentCity = MutableStateFlow<String?>(null)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadLocalWeather()
    }

    fun loadLocalWeather() {
        viewModelScope.launch {
            _isLoading.value = true
            repo.getLocalWeather().collectLatest { list ->
                _weatherList.value = list
                if (list.isNotEmpty()) {
                    currentCity.value = list.first().city
                }
            }
            _isLoading.value = false
        }
    }

    fun refreshWeather(lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repo.fetchAndSaveWeather(lat, lon, apiKey)
                loadLocalWeather() // reload local data after fetching
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}


