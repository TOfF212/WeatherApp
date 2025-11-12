package com.hfad.weather.domain

import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeather(city: String): Flow<List<Weather>>


    suspend fun findCity(city: String): String

}