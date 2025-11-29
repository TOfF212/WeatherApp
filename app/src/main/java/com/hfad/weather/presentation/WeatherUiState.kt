package com.hfad.weather.presentation

import android.os.Message
import com.hfad.weather.domain.Weather

sealed class WeatherUiState {
    object Loading: WeatherUiState()
    data class Success(val list: List<Weather>): WeatherUiState()
    data class Error(val message: String): WeatherUiState()

}