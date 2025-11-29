package com.hfad.weather.presentation.components.ListItems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hfad.weather.domain.Weather
import com.hfad.weather.ui.theme.Pink40
import com.hfad.weather.ui.theme.Pink80


    @Composable
    fun ListHourItem(item: Weather, onClick: () -> Unit) {
        Card(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .alpha(1f)
                .padding(top = 5.dp)
                .clickable { onClick() },
            colors = CardDefaults.cardColors(containerColor = Pink80, contentColor = Pink40),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Row(
                Modifier.Companion
                    .fillMaxWidth()
                    .padding(5.dp),
                Arrangement.SpaceBetween, verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Column(Modifier.Companion.padding(start = 10.dp)) {
                    Text(
                        text = item.time, style = TextStyle(fontSize = 20.sp),
                    )
                    Text(
                        text = item.condition, style = TextStyle(fontSize = 20.sp),
                    )
                }
                Text(
                    text = item.currentTemp.ifEmpty { item.maxTemp + "/" + item.minTemp },
                    style = TextStyle(fontSize = 30.sp),
                )

                AsyncImage(
                    model = "https:${item.conditionIcon}",
                    contentDescription = "im2",
                    modifier = Modifier.Companion
                        .size(35.dp)
                        .padding(top = 8.dp, end = 8.dp)
                )
            }
        }
    }
