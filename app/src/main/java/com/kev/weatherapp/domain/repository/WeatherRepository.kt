package com.kev.weatherapp.domain.repository

import com.kev.weatherapp.BuildConfig
import com.kev.weatherapp.data.dto.WeatherResponseDto
import com.kev.weatherapp.data.remote.WeatherApiService
import javax.inject.Inject

interface WeatherRepository {
	suspend fun fetchTodayWeather(location:String) : WeatherResponseDto
	suspend fun fetchWeatherForecast(location: String) : WeatherResponseDto
}