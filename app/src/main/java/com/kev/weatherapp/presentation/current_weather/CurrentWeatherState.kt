package com.kev.weatherapp.presentation.current_weather

import com.kev.weatherapp.domain.model.CurrentWeatherDomainModel

data class CurrentWeatherState(
	val weatherInfo : CurrentWeatherDomainModel,
	val isLoading:Boolean = false,
	val error:String? = null
)
