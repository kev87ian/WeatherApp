package com.kev.weatherapp.domain.use_case.current_weather_usecase

import com.bumptech.glide.load.engine.Resource
import com.kev.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Flow
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
	private val repository: WeatherRepository
){
	suspend fun fetchCurrentWeather(location:String)  = withContext(Dispatchers.IO){
		repository.fetchCurrentWeatherDetails(location)
	}
}