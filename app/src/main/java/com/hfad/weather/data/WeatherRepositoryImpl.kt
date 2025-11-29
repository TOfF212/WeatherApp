package com.hfad.weather.data

import android.util.Log
import com.hfad.weather.domain.Weather
import com.hfad.weather.domain.WeatherRepository
import com.hfad.weather.domain.WeatherResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class WeatherRepositoryImpl(private val context: android.content.Context): WeatherRepository {

    private val weatherApi = WeatherApi(context)

    override fun getWeather(city: String): Flow<WeatherResult<List<Weather>>> =
        weatherApi.getWeatherResponse(city)
            .map { response ->
                if(response.isNotEmpty()){
                    WeatherResult.Success(WeatherMapper().getWeatherByDays(response))
                } else{
                   WeatherResult.Error("Result Empty")
                }
            }
            .onStart {
                emit(WeatherResult.Loading)
            }
            .catch {e ->
                WeatherResult.Error(e.message?: "Unknown Error")
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