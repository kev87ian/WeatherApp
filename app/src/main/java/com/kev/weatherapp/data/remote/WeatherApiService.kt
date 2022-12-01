package com.kev.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

	@GET("current.json")
	suspend fun fetchWeather(


	)
}