package com.kev.weatherapp.data.remote

import androidx.viewbinding.BuildConfig
import com.kev.weatherapp.data.dto.CurrentWeatherDto
import com.kev.weatherapp.data.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
//https://api.weatherapi.com/v1/forecast.json?key=key&q=-1.285790,36.820030&days=1&aqi=no&alerts=no
	@GET("forecast.json")
	suspend fun fetchCurrentWeatherDetails(
		@Query("key") apiKey : String,
		@Query("q", encoded = true) location: String,

		@Query("days") numberOfDays: Int = 1

	) : WeatherDto

	@GET("forecast.json")
	suspend fun fetchWeatherForecast(
		@Query("key") apiKey : String,
		@Query("q", encoded = true) location: String,
		@Query("days") numberOfDays: Int = 5

	) : WeatherDto


}