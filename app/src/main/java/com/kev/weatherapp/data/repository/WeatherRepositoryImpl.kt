package com.kev.weatherapp.data.repository

import com.kev.weatherapp.BuildConfig
import com.kev.weatherapp.data.dto.WeatherResponseDto
import com.kev.weatherapp.data.remote.WeatherApiService
import com.kev.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
	private val apiService: WeatherApiService
) : WeatherRepository {

	override suspend fun fetchTodayWeather(location: String): WeatherResponseDto {

		return apiService.fetchTodayWeather(BuildConfig.API_KEY, location)
	}

	override suspend fun fetchWeatherForecast(location: String): WeatherResponseDto {

		return apiService.fetchWeatherForecast(BuildConfig.API_KEY, location)
	}
}