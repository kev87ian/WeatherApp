package com.kev.weatherapp.data.remote

import androidx.viewbinding.BuildConfig
import com.kev.weatherapp.data.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

	@GET("forecast.json")
	suspend fun fetchWeatherDetails(
		@Query("key") apiKey : String,
		@Query("q") location: String,
		@Query("days") numberOfDays: Int = 10,

	) : WeatherDto
}