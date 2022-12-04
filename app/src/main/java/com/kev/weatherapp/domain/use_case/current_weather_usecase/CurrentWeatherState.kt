package com.kev.weatherapp.domain.use_case.current_weather_usecase

import com.kev.weatherapp.data.dto.Condition

data class CurrentWeatherState (
	val condition:Condition? = null,
	val isLoading : Boolean = false,
	val error:String? = null

		)