package com.hfad.weather.domain

sealed class WeatherResult<out T> {
    object Loading: WeatherResult<Nothing>()
    data class Success<T>(val data: T): WeatherResult<T>()
    data class Error(val message: String): WeatherResult<Nothing>()
}