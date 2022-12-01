package com.kev.weatherapp.data.remote

import androidx.viewbinding.BuildConfig
import com.kev.weatherapp.data.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

	@GET("forecast.json")
	suspend fun fetchCurrentWeatherDetails(
		@Query("key") apiKey : String,
		@Query("q") location: String,
		@Query("days") numberOfDays: Int = 1

	) : WeatherDto

	@GET("forecast.json")
	suspend fun fetchWeatherForecast(
		@Query("key") apiKey : String,
		@Query("q") location: String,
		@Query("days") numberOfDays: Int = 3

	) : WeatherDto


}