package com.kev.weatherapp.domain.model

import com.kev.weatherapp.data.dto.Condition

data class WeatherDomainModel(
	val tempC: Double,
	val condition: Condition,
	val isDay: Int,
	val humidity: Int,
	val windKph: Double,
	val feelsLikeC:Double
)
