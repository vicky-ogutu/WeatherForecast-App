package com.example.weatherapp.UserInterface

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.WeatherConstants
import com.example.weatherapp.models.WeatherItem
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MainScreen(viewModel: WeatherViewModel) {
    val weatherList by viewModel.weatherList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // ===== Header Card: City + Refresh =====
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
                    text = "Nairobi, Kenya",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

//                Icon(
//                    imageVector = Icons.Filled.Refresh,
//                    contentDescription = "Refresh",
//                    modifier = Modifier
//                        .size(28.dp)
//                        .clickable {
//                            viewModel.refreshWeather(
//                                -1.2864,
//                                36.8172,
//                                WeatherConstants.WEATHER_API_KEY
//                            )
//                        }
//                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== Current Weather Card =====
        if (weatherList.isNotEmpty()) {
            val currentWeather = weatherList.first()
            CurrentWeatherCard(currentWeather)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== Forecast List =====
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(weatherList) { weather ->
                WeatherCard(weather)
            }
        }
    }
}

@Composable
fun CurrentWeatherCard(weather: WeatherItem) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "${weather.temp}°C", fontWeight = FontWeight.Bold)
                Text(text = "Hum: ${weather.humidity}%", fontWeight = FontWeight.Medium)
                Text(text = "Wind: ${weather.windSpeed} m/s", fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherItem) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = formatDate(weather.dt_txt, "EEE HH:mm"),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = weather.description,
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "${weather.temp}°C", fontWeight = FontWeight.Bold)
                Text(text = "Hum: ${weather.humidity}%", fontWeight = FontWeight.Medium)
                Text(text = "Wind: ${weather.windSpeed} m/s", fontWeight = FontWeight.Medium)
            }
        }
    }
}

// ===== Helper function to format date string =====
fun formatDate(dateStr: String, pattern: String): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = sdf.parse(dateStr)
        SimpleDateFormat(pattern, Locale.getDefault()).format(date!!)
    } catch (e: Exception) {
        dateStr
    }
}

