package com.example.weatherapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.ui.Modifier
import com.example.weatherapp.UserInterface.WeatherViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.weatherapp.Api.RetrofitInstance
import com.example.weatherapp.Api.WeatherApiService
import com.example.weatherapp.Database.WeatherDatabase
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.UserInterface.MainScreen
import com.example.weatherapp.UserInterface.WeatherViewModelFactory


class MainActivity : ComponentActivity() {

    private val repository by lazy {
        WeatherRepository(
            api = RetrofitInstance.api,
             dao = WeatherDatabase.getDatabase(this).weatherDao()
        )
    }

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.loadLocalWeather()
        viewModel.refreshWeather(-1.2864, 36.8172, WeatherConstants.WEATHER_API_KEY)

        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}











