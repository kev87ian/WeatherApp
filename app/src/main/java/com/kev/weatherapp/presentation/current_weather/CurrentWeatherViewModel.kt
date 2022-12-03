package com.kev.weatherapp.presentation.current_weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.kev.weatherapp.domain.location.LocationTracker
import com.kev.weatherapp.domain.model.CurrentWeatherDomainModel
import com.kev.weatherapp.domain.use_case.current_weather_usecase.GetCurrentWeatherUseCase
import com.kev.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
	private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
	private val locationTracker: LocationTracker
) : ViewModel() {


	private val _currentWeatherLiveData = MutableLiveData<Resource<CurrentWeatherDomainModel>>()
	val currentWeatherLiveData: LiveData<Resource<CurrentWeatherDomainModel>> =
		_currentWeatherLiveData


	private val _locationPermissionObservable = MutableLiveData<String>()
	val locationPermissionObservable: LiveData<String> = _locationPermissionObservable


	fun fetchCurrentWeather() = viewModelScope.launch {
	/*	locationTracker.getCurrentLocation()?.let { location ->

			getCurrentWeatherUseCase.fetchCurrentWeather(location.latitude.toString().plus(location.longitude)).collect{
				_currentWeatherLiveData.postValue(it)
			}
		}?: kotlin.run {
			_locationPermissionObservable.postValue("Ensure you have granted gps permission")
		}*/

		val latitude = locationTracker.getCurrentLocation()?.latitude.toString()
		val longitude = locationTracker.getCurrentLocation()?.longitude.toString()

		if (longitude.isNotEmpty()||latitude.isNotEmpty()){
			getCurrentWeatherUseCase.fetchCurrentWeather(latitude.plus(",".plus(longitude))).collect{
				_currentWeatherLiveData.postValue(it)
			}
		}

		else{
			_locationPermissionObservable.postValue("Hauna permission za net")
		}

	}


	init {
		fetchCurrentWeather()
	}
}