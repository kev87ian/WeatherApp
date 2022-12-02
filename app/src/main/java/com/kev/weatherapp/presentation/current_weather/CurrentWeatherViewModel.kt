package com.kev.weatherapp.presentation.current_weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kev.weatherapp.domain.location.LocationTracker
import com.kev.weatherapp.domain.model.WeatherDomainModel
import com.kev.weatherapp.domain.use_case.current_weather_usecase.GetCurrentWeatherUseCase
import com.kev.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
	private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
	private val locationTracker: LocationTracker
) : ViewModel() {


	private val _currentWeatherLiveData = MutableLiveData<Resource<WeatherDomainModel>>()
	val currentWeatherLiveData: LiveData<Resource<WeatherDomainModel>> = _currentWeatherLiveData


	private val _locationPermissionObservable = MutableLiveData<String>()
	val locationPermissionObservable: LiveData<String> = _locationPermissionObservable

	fun fetchCurrentWeather() = viewModelScope.launch {

		locationTracker.getCurrentLocation()?.let { location ->
			getCurrentWeatherUseCase.fetchCurrentWeather(
				location.latitude.toString().plus(location.longitude)
			).onEach { result ->

				when (result) {
					is Resource.Success -> {
						_currentWeatherLiveData.postValue(Resource.Success(result.data!!))
					}

					is Resource.Error -> {
						_currentWeatherLiveData.postValue(
							Resource.Error(
								result.message ?: "An error occured."
							)
						)
					}

					is Resource.Loading -> {
						_currentWeatherLiveData.postValue(Resource.Loading())
					}

				}

			}
		}//incase we don't get a location
			?: kotlin.run {
				//TODO Find a better implementation of this error handling
				_locationPermissionObservable.postValue("Couldn't retrieve your location. Please grant GPS permission.")
			}


	}


	init {
		fetchCurrentWeather()
	}
}