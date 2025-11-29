package com.hfad.weather.presentation

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.weather.data.WeatherRepositoryImpl
import com.hfad.weather.domain.Weather
import com.hfad.weather.domain.WeatherResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context
        get() = getApplication<Application>().applicationContext

    private val repository = WeatherRepositoryImpl(context)

    private val _currentCity = MutableStateFlow("Saint Petersburg")

    private val refreshTick = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1)

    private val _dialogState = mutableStateOf(false)
    val dialogState: State<Boolean> get() = _dialogState

    private val _dialogError = mutableStateOf(false)
    val dialogError: State<Boolean> get() = _dialogError

    private val EMPTY_WEATHER = Weather(
        "","","", "","","","",emptyList(), "",""
    )

    val state: StateFlow<WeatherUiState> =
        combine(
            _currentCity,
            refreshTick.onStart {emit(Unit) }
        ){
            city, _ -> city
        }.flatMapLatest { city ->
                repository.getWeather(city)
                    .map { res ->
                        when(res){
                            is WeatherResult.Loading -> WeatherUiState.Loading
                            is WeatherResult.Error -> WeatherUiState.Error(res.message)
                            is WeatherResult.Success -> WeatherUiState.Success(res.data)
                        }
                    }
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, WeatherUiState.Loading)



    private val lastSuccessList: List<Weather> = emptyList()
    private val _selectedDay = MutableStateFlow<Weather?>(null)
    val currentDay: StateFlow<Weather> =
        combine(state, _selectedDay) { state, selected ->
            if (state is WeatherUiState.Success){
                val list = state.list
                val safeSelected = selected?.takeIf { s -> list.any { it.time == s.time && it.city == s.city } }
                safeSelected ?: list.firstOrNull() ?: EMPTY_WEATHER
            } else{
                EMPTY_WEATHER
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, EMPTY_WEATHER)

    fun openDialog() {
        _dialogState.value = true
        _dialogError.value = false
    }

    fun closeDialog() {
        _dialogState.value = false
        _dialogError.value = false
    }

    fun changeCurrentDay(newCurrday: Weather) {
        _selectedDay.value = newCurrday
    }

    fun refreshWeather() {
        refreshTick.tryEmit(Unit)
    }

    fun newCity(newCity: String) {
        viewModelScope.launch {
            val query = newCity.trim()
            if (query.isEmpty()) {
                _dialogError.value = true
                return@launch
            }

            try {
                val foundCity = repository.findCity(query)
                if (foundCity.isNotEmpty()) {
                    _currentCity.value = foundCity
                    _selectedDay.value = null
                    closeDialog()
                } else {
                    _dialogError.value = true
                }
            } catch (_: Exception) {
                _dialogError.value = true
            }
        }
    }
}