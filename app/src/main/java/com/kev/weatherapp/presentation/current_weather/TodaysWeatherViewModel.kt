package com.kev.weatherapp.presentation.current_weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.weatherapp.domain.usecase.today_weather.GetTodayWeatherUseCase
import com.kev.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodaysWeatherViewModel @Inject constructor(
	private val useCase: GetTodayWeatherUseCase
) : ViewModel() {

	private val _dataState = MutableLiveData<TodaysWeatherUIState>()
	 val dataState: LiveData<TodaysWeatherUIState> get() = _dataState



	fun getTodaysWeather(location:String) = viewModelScope.launch {
		val state = TodaysWeatherUIState(isLoading = false, null, null)
		when(val result = useCase.fetchTodayWeather(location)){



			is Resource.Success ->{
				_dataState.value = TodaysWeatherUIState(weatherInfo = result.data!!)
			}

			is Resource.Loading->{
				_dataState.value = TodaysWeatherUIState(isLoading = true, null, null)
			}

			is Resource.Error->{
				_dataState.value = TodaysWeatherUIState(false, error = result.message ?: "An error occured", null)
			}

		}
	}

}