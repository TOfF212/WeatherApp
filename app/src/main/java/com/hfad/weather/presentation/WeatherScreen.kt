package com.hfad.weather.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.hfad.weather.R
import com.hfad.weather.presentation.components.DialogSearch
import com.hfad.weather.presentation.components.ForecastList
import com.hfad.weather.presentation.components.WeatherCard
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val scope = rememberCoroutineScope()
    val daysList by viewModel.daysList.collectAsState()
    val currentDay by viewModel.currentDay.collectAsState()
    val dialogState by viewModel.dialogState
    val dialogError by viewModel.dialogError

    Image(
        painter = painterResource(id = R.drawable.sky_cat),
        contentDescription = "background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )


    Column(modifier = Modifier.fillMaxSize()) {
        WeatherCard(
            currentDay,
            {viewModel.openDialog()},
            {viewModel.refreshWeather()}
        )

        ForecastList(
            daysList,
            currentDay,
            {day ->viewModel.changeCurrentDay(day)}
        )

        if (dialogState){
            DialogSearch(
                error = dialogError,
                onConfirm = { cityName -> viewModel.newCity(cityName) },
                onDismiss = { viewModel.closeDialog() }
            )
        }

    }
}