This App uses Kotlin 2.0.21, jetpack compose, Retrofit and Room

Below is the MVVM architecture of the app.

com.example.weatherApp
|
|    |
|    |-- User-Interface 
|    |     |--  MainScreen
|    |     |-- Details.screen
|    |     
|    |
|    |-- Databse
|    |     |-- WeatherDatabase.kt
|    |
|    |-- models
|    |     |-- City
|    |     |-- ForecastItem
|    |.    |-- ForecastResponse
|    |     |-- Main
|    |     |-- Rain
|    |.    |-- Weather
|    |     |-- WeatherDescription
|    |.    |-- WeatherItem (Entity)
|    |     |-- WeatherTypeConverter
|    |.    |-- Wind
|    |   
|    |-- Dao
|    |     |-- WeatherDao
|    |   
|    |-- Repository
|    |     |-- WeatherRepository
|    |
|    |-- viewmodel
|    | |-- WeatherViewModel
|    |
|    |-- Api
|    |     |-- WeatherApiService
|    |     |-- RetrofitInstance
|    |
|    |-- viewmodel
|    | |-- WeatherViewModel
|    |
|    |-- Utils
|    |     |-- WeatherConstants
|    |
|-- MainActivity.kt
