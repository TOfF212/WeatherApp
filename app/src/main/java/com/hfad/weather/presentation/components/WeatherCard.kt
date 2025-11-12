package com.hfad.weather.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hfad.weather.R
import com.hfad.weather.domain.Weather
import com.hfad.weather.ui.theme.Pink40
import com.hfad.weather.ui.theme.Pink80
import kotlin.text.ifEmpty

@Composable
fun WeatherCard(currentDay: Weather,
                onSearchClick: () -> Unit,
                onRefreshClick: () -> Unit) {


    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.7f)
                .padding(top = 25.dp),
            colors = CardDefaults.cardColors(containerColor = Pink80, contentColor = Pink40),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = currentDay.time,
                        style = TextStyle(fontSize = 15.sp)
                    )
                    AsyncImage(
                        model = "https:${currentDay.conditionIcon}",
                        contentDescription = "im2",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(top = 8.dp, end = 8.dp)
                    )
                }
                Text(
                    text = currentDay.city, style = TextStyle(fontSize = 24.sp),
                )
                Text(
                    text = currentDay.currentTemp.ifEmpty { currentDay.maxTemp + "/" + currentDay.minTemp }, style = TextStyle(fontSize = 50.sp),
                )
                Text(
                    text = currentDay.condition, style = TextStyle(fontSize = 16.sp),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            painterResource(id = R.drawable.ic_search_24),
                            contentDescription = "im3",
                        )
                    }
                    Text(
                        text = "${currentDay.maxTemp}/${currentDay.minTemp}", style = TextStyle(fontSize = 16.sp),
                    )
                    IconButton(onClick = onRefreshClick) {
                        Icon(
                            painterResource(id = R.drawable.ic_refresh_24),
                            contentDescription = "im3",
                        )
                    }
                }
            }
        }
    }
}