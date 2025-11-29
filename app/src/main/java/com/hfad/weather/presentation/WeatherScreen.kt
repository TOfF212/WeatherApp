package com.hfad.weather.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.hfad.weather.R
import com.hfad.weather.presentation.components.DialogSearch
import com.hfad.weather.presentation.components.ErrorView
import com.hfad.weather.presentation.components.ForecastList
import com.hfad.weather.presentation.components.WeatherCard
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val uiState by viewModel.state.collectAsState()
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
        when(uiState){
            is WeatherUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            is WeatherUiState.Success -> {
                val list = (uiState as WeatherUiState.Success).list

                WeatherCard(
                    currentDay,
                    {viewModel.openDialog()},
                    {viewModel.refreshWeather()}
                )

                ForecastList(
                    list,
                    currentDay,
                    {day ->viewModel.changeCurrentDay(day)}
                )
            }
            is WeatherUiState.Error -> {
                ErrorView((uiState as WeatherUiState.Error).message)
            }
        }


        if (dialogState){
            DialogSearch(
                error = dialogError,
                onConfirm = { cityName -> viewModel.newCity(cityName) },
                onDismiss = { viewModel.closeDialog() }
            )
        }

    }
}