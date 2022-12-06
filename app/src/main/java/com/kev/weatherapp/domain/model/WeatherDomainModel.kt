package com.kev.weatherapp.domain.model

import com.kev.weatherapp.data.dto.Forecast
import com.kev.weatherapp.data.dto.Location



data class  WeatherDomainModel(
	val current: CurrentWeatherDomainModel?,
	val forecast: Forecast?,
	val location: Location?
)