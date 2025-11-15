package com.example.weatherapp.Dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.models.WeatherItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertWeather(weather: WeatherEntity)
//
//    @Query("SELECT * FROM weather_table WHERE city = :city LIMIT 1")
//    fun getWeather(city: String): Flow<WeatherEntity?>



    @Query("SELECT * FROM weather_table ORDER BY dt ASC")
    fun getWeatherList(): Flow<List<WeatherItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<WeatherItem>)
}
