package com.example.weatherapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity(tableName = "weather_table")
data class WeatherItem(
    @PrimaryKey val dt: Long,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String,
    val dt_txt: String,
    val city: String?,
    val rainVolume: Double?
) : Parcelable

