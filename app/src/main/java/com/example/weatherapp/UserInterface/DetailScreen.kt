package com.example.weatherapp.UserInterface


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.models.WeatherItem

@Composable
fun DetailScreen(weather: WeatherItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp), // top margin for the header
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Weather Details",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val iconUrl = "https://openweathermap.org/img/wn/${weather.icon}@4x.png"
                Image(
                    painter = rememberAsyncImagePainter(iconUrl),
                    contentDescription = weather.description,
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = weather.description, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Date: ${weather.dt_txt}")
                Text(text = "Temp: ${weather.temp}°C (Min: ${weather.tempMin}°C, Max: ${weather.tempMax}°C)")
                Text(text = "Humidity: ${weather.humidity}%")
                Text(text = "Wind Speed: ${weather.windSpeed} m/s")
            }
        }
    }
}
