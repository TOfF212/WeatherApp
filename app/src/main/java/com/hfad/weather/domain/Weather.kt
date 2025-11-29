package com.hfad.weather.domain

data class Weather(
    val city: String,
    val time: String,
    val currentTemp: String,
    val condition: String,
    val conditionIcon: String,
    val minTemp: String,
    val maxTemp: String,
    val hours: List<Weather>,
    val wind: String,
    val chancePrecipitation: String
) {

}