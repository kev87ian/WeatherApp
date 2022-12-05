package com.kev.weatherapp.domain.use_case.current_weather_usecase

import com.kev.weatherapp.domain.model.WeatherDomainModel

data class WeatherState(
	val isLoading : Boolean =false,
	val error:String? = null,
	val weatherData : WeatherDomainModel? = null
)
