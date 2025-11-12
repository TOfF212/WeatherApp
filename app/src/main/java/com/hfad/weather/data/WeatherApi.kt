package com.hfad.weather.data

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class WeatherApi(private val context: android.content.Context) {
    val API_KEY = ""

    fun getWeatherResponse(city: String): Flow<String> = callbackFlow{
        val url = "https://api.weatherapi.com/v1/forecast.json" +
                "?key=$API_KEY"+
                "&q=$city" +
                "&days=14"+
                "&aqi=no" +
                "&alerts=no"
        val queue = Volley.newRequestQueue(context)
        var result = ""

        val sRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                    response ->
                trySend(response)
                close()
            },
            {
                    error ->

                Log.e("MyLog","Error: $error")
                close(error)
            })
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        scope.async {
            queue.add(sRequest)
        }
        awaitClose {
        }
    }


}