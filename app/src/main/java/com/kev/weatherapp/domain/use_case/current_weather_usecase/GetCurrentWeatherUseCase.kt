package com.kev.weatherapp.domain.use_case.current_weather_usecase

import com.kev.weatherapp.data.dto.WeatherDto
import com.kev.weatherapp.data.dto.toWeatherDomainModel
import com.kev.weatherapp.domain.model.CurrentWeatherDomainModel
import com.kev.weatherapp.domain.model.WeatherDomainModel
import com.kev.weatherapp.domain.repository.WeatherRepository
import com.kev.weatherapp.util.Resource
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
	private val repository: WeatherRepository,
) {
	suspend fun fetchCurrentWeather(location:String): Resource<WeatherDomainModel> {
		return try {
				Resource.Loading<WeatherDomainModel>()
				val weatherData = repository.fetchCurrentWeatherDetails(location).toWeatherDomainModel()
				Resource.Success(weatherData)

		} catch (e: Exception) {
			e.printStackTrace()
			when (e) {
				is IOException -> Resource.Error("No internet connection")
				is HttpException -> Resource.Error("Couldn't reach server")
				else -> Resource.Error(e.localizedMessage!!)
			}
		}
	}



}