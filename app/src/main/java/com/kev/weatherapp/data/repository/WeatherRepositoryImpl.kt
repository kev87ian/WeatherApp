package com.kev.weatherapp.data.repository

import com.kev.weatherapp.BuildConfig
import com.kev.weatherapp.data.dto.WeatherDto
import com.kev.weatherapp.data.remote.WeatherApiService
import com.kev.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
	private val apiService: WeatherApiService
) : WeatherRepository {

	override suspend fun fetchCurrentWeatherDetails(location: String): WeatherDto {
		return apiService.fetchCurrentWeatherDetails(BuildConfig.API_KEY, location)
	}

	override suspend fun fetchWeatherForecast(location: String): WeatherDto {
		return apiService.fetchWeatherForecast(BuildConfig.API_KEY, location)
	}
}