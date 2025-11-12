package com.hfad.weather.data

import android.util.Log
import com.hfad.weather.domain.Weather
import com.hfad.weather.domain.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(private val context: android.content.Context): WeatherRepository {

    private val weatherApi = WeatherApi(context)

    override fun getWeather(city: String): Flow<List<Weather>> {
        return weatherApi.getWeatherResponse(city).map { response ->
            if(response.isNotEmpty()){
                WeatherMapper().getWeatherByDays(response)
            } else{
               emptyList()
            }
        }.catch {e -> emit(emptyList())  }
    }

    override suspend fun findCity(city: String): String {
        return try {
            val response: String = weatherApi.getWeatherResponse(city).first()
            WeatherMapper().getCity(response)
        } catch (e: Exception) {
            ""
        }
    }

}