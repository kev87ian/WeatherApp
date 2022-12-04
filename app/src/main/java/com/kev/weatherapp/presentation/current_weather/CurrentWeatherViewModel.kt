package com.kev.weatherapp.presentation.current_weather

import androidx.lifecycle.*
import com.kev.weatherapp.domain.model.CurrentWeatherDomainModel
import com.kev.weatherapp.domain.use_case.current_weather_usecase.GetCurrentWeatherUseCase
import com.kev.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
	private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,


	) : ViewModel() {


	private val _currentWeatherLiveData = MutableLiveData<Resource<CurrentWeatherDomainModel>>()
	val currentWeatherLiveData: LiveData<Resource<CurrentWeatherDomainModel>> =
		_currentWeatherLiveData


	fun getCurrentWeather(location:String) = viewModelScope.launch {


		when (val result = getCurrentWeatherUseCase.fetchCurrentWeather(location)) {
			is Resource.Error -> {

				_currentWeatherLiveData.postValue(
					Resource.Error(
						result.message ?: "VM level error handling"
					)
				)
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