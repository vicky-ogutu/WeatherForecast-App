package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.UserInterface.DetailScreen
import com.example.weatherapp.UserInterface.MainScreen
import com.example.weatherapp.UserInterface.WeatherViewModel
import com.example.weatherapp.UserInterface.WeatherViewModelFactory
import com.example.weatherapp.Database.WeatherDatabase
import com.example.weatherapp.Repository.WeatherRepository
import com.example.weatherapp.Utils.WeatherConstants

class MainActivity : ComponentActivity() {

    private val repository by lazy {
        WeatherRepository(
            api = com.example.weatherapp.Api.RetrofitInstance.api,
            dao = WeatherDatabase.getDatabase(this).weatherDao()
        )
    }

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initial load
        viewModel.loadLocalWeather()
        viewModel.refreshWeather(-1.2864, 36.8172, WeatherConstants.WEATHER_API_KEY)

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "main"
            ) {

                composable("main") {
                    MainScreen(
                        viewModel = viewModel,
                        navController = navController
                    )
                }

                composable("detail") {
                    val weatherItem =
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.get<com.example.weatherapp.models.WeatherItem>("weather")

                    weatherItem?.let { DetailScreen(it) }
                }
            }
        }
    }
}












