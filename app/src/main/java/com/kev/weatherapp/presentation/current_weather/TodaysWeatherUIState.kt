package com.kev.weatherapp.presentation.current_weather

import com.kev.weatherapp.data.dto.WeatherResponseDto


data class TodaysWeatherUIState(
	val isLoading:Boolean = false,
	val error:String? = null,
	val weatherInfo : WeatherResponseDto?,

)
