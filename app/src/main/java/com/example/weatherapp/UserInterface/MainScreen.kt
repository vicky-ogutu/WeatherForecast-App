package com.example.weatherapp.UserInterface

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.Utils.WeatherConstants
import com.example.weatherapp.models.WeatherItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("MissingPermission")
@Composable
fun MainScreen(viewModel: WeatherViewModel,  navController: NavController) {
    val context = LocalContext.current
    val weatherList by viewModel.weatherList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var userLocation by remember { mutableStateOf<Location?>(null) }

    // Location permission launcher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                getCurrentLocation(context) { location ->
                    userLocation = location
                    viewModel.refreshWeather(
                        location.latitude,
                        location.longitude,
                        WeatherConstants.WEATHER_API_KEY
                    )
                }
            } else {
                // Fallback to Tokyo
                viewModel.refreshWeather(
                    35.6895,
                    139.6917,
                    WeatherConstants.WEATHER_API_KEY
                )
            }
        }
    )

    // Request location permission on first composition
    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header: City + Refresh
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = userLocation?.let { "Your Location" } ?: "Weather Forecast",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Refresh",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            val lat = userLocation?.latitude ?: -1.2864
                            val lon = userLocation?.longitude ?: 36.8172
                            viewModel.refreshWeather(lat, lon, WeatherConstants.WEATHER_API_KEY)
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pull-to-refresh
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isLoading),
            onRefresh = {
                val lat = userLocation?.latitude ?: -1.2864
                val lon = userLocation?.longitude ?: 36.8172
                viewModel.refreshWeather(lat, lon, WeatherConstants.WEATHER_API_KEY)
            }
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(weatherList) { weather ->
                    WeatherCard(weather = weather, onClick = {
                        // Navigate to detail screen
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("weather", weather)

                        navController.navigate("detail")


                    })
                }
            }
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherItem, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            val iconUrl = "https://openweathermap.org/img/wn/${weather.icon}@2x.png"
            Image(
                painter = rememberAsyncImagePainter(iconUrl),
                contentDescription = weather.description,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = formatDate(weather.dt_txt, "EEE, MMM dd - HH:mm"),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = weather.description,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Temp: ${weather.temp}°C", fontWeight = FontWeight.Bold)
                Text(text = "Min: ${weather.tempMin}°C, Max: ${weather.tempMax}°C")
                Text(text = "Humidity: ${weather.humidity}%")
                Text(text = "Wind: ${weather.windSpeed} m/s")
            }
        }
    }
}


fun formatDate(dateStr: String, pattern: String): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = sdf.parse(dateStr)
        SimpleDateFormat(pattern, Locale.getDefault()).format(date!!)
    } catch (e: Exception) {
        dateStr
    }
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation(context: Context, onLocation: (Location) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let { onLocation(it) }
    }
}
