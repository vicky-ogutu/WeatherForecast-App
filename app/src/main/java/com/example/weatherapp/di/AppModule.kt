package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.Api.WeatherApiService
import com.example.weatherapp.Database.WeatherDatabase
import com.example.weatherapp.Repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): WeatherDatabase =
        Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_db").build()

    @Provides
    fun provideWeatherDao(db: WeatherDatabase) = db.weatherDao()

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: WeatherApiService, dao: WeatherDatabase) = WeatherRepository(api, dao.weatherDao())
}
