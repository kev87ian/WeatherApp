package com.kev.weatherapp.presentation.current_weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.weatherapp.domain.model.WeatherDomainModel
import com.kev.weatherapp.domain.use_case.current_weather_usecase.GetCurrentWeatherUseCase
import com.kev.weatherapp.domain.use_case.current_weather_usecase.WeatherState
import com.kev.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
	private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,

	) : ViewModel() {

	private val _currentWeatherLiveData = MutableLiveData<Resource<WeatherDomainModel>>()
	val currentWeatherLiveData: LiveData<Resource<WeatherDomainModel>> =
		_currentWeatherLiveData


	fun getCurrentWeather(location:String) = viewModelScope.launch {


		when (val result = getCurrentWeatherUseCase.fetchCurrentWeather(location)) {
			is Resource.Error -> {
				_currentWeatherLiveData.postValue(Resource.Error(result.message ?: "VM level error handling"))
			}

			is Resource.Loading -> {
				_currentWeatherLiveData.postValue(Resource.Loading())
			}

			is Resource.Success -> {
				_currentWeatherLiveData.postValue(Resource.Success(result.data!!))
			}
		}

	}


}