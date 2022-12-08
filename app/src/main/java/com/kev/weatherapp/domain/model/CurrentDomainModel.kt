package com.kev.weatherapp.domain.model

import com.google.gson.annotations.SerializedName
import com.kev.weatherapp.data.dto.Condition


data class CurrentDomainModel(
	val condition: Condition,
	val feelsLikeC: Double,
	val humidity: Int,
	val isDay: Int,
	val lastUpdated: String,
	val tempC: Double,
	val windKph: Double
)