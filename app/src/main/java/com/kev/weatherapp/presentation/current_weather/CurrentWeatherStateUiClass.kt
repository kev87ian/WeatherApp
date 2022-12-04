package com.kev.weatherapp.presentation.current_weather

import com.kev.weatherapp.domain.model.CurrentWeatherDomainModel

data class CurrentWeatherStateUiClass(
	val isLoading:Boolean = false,
	val error:String? = null,
	val weatherInfo : CurrentWeatherDomainModel?,

)
