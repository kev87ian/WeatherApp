package com.kev.weatherapp.presentation.current_weather

import com.kev.weatherapp.domain.model.WeatherDomainModel

data class CurrentWeatherState(
	val weatherInfo : WeatherDomainModel,
	val isLoading:Boolean = false,
	val error:String? = null
)
