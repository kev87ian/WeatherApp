package com.kev.weatherapp.domain.repository

import com.kev.weatherapp.data.dto.CurrentWeatherDto
import com.kev.weatherapp.data.dto.WeatherDto

interface WeatherRepository {

	suspend fun fetchCurrentWeatherDetails(location:String) : CurrentWeatherDto
	suspend fun fetchWeatherForecast(location: String):WeatherDto

}