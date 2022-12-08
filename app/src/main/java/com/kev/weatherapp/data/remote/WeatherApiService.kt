package com.kev.weatherapp.data.remote

import com.kev.weatherapp.data.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

	@GET("forecast.json")
	suspend fun fetchTodayWeather(
		@Query("key") apiKey: String,
		@Query("q", encoded = true) location: String,

		@Query("days") numberOfDays: Int = 1

	): WeatherResponseDto

	@GET("forecast.json")
	suspend fun fetchWeatherForecast(
		@Query("key") apiKey: String,
		@Query("q", encoded = true) location: String,
		@Query("days") numberOfDays: Int = 5

	): WeatherResponseDto



}


