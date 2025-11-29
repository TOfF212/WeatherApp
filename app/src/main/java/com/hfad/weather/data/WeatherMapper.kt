package com.hfad.weather.data

import com.hfad.weather.domain.Weather
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Integer.max

class WeatherMapper {

    fun getWeatherByDays(response: String): List<Weather>{
        if (response.isEmpty()) return listOf()
        val mainObject = JSONObject(response)
        val days = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        val result = mutableListOf<Weather>()
        val city = mainObject.getJSONObject("location").getString("name")
        days.forEachObject { day ->
            val dayData = day.getJSONObject("day")
            val condition = dayData.getJSONObject("condition")
            val chance = max(
                dayData.getInt("daily_chance_of_snow"),
                dayData.getInt("daily_chance_of_rain")
            ).toString()
            result.add(Weather(
                city = city,
                day.getString("date"),
                "",
                condition.getString("text"),
                condition.getString("icon"),
                dayData.getString("mintemp_c"),
                dayData.getString("maxtemp_c"),
                getWeatherByHours(day.optString("hour", ""), city),
                 dayData.getString("maxwind_kph"),
                chance
            ))
        }
        result[0] = result[0].copy(
            time = mainObject.getJSONObject("current").getString("last_updated"),
            currentTemp = mainObject.getJSONObject("current").getString("temp_c")
        )
        return result.toList()

    }
    fun getWeatherByHours(hoursStr: String, city: String): List<Weather>{
        if (hoursStr.isEmpty()) return emptyList()
        val hours = JSONArray(hoursStr)
        val result = mutableListOf<Weather>()
        hours.forEachObject { hour ->
            val condition = hour.getJSONObject("condition")

            result.add(Weather(
                city,
                hour.getString("time").split(" ")[1],
                hour.getString("temp_c"),
                condition.getString("text"),
                condition.getString("icon"),
                "",
                "",
                emptyList(),
                hour.getString("wind_kph"),
                max(hour.getString("chance_of_snow").toInt(), hour.getString("chance_of_rain").toInt()).toString()
            ))
        }
        return result.toList()
    }

    fun getCity(response: String): String{
        var city: String
        try{
            val mainObject = JSONObject(response)
            city = mainObject.getJSONObject("location").getString("name")
        } catch (e: Exception){
            city = ""
        }
        return city
    }



}



private fun JSONArray.forEachObject(block: (JSONObject) -> Unit) {
    for (i in 0 until this.length()) {
        block(this.getJSONObject(i))
    }
}
