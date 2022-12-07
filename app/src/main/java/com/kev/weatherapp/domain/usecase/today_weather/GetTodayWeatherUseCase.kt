package com.kev.weatherapp.domain.usecase.today_weather

import com.kev.weatherapp.data.dto.WeatherResponseDto
import com.kev.weatherapp.domain.repository.WeatherRepository
import com.kev.weatherapp.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTodayWeatherUseCase @Inject constructor(
	private val repository: WeatherRepository
) {

	suspend fun fetchTodayWeather(location: String): Resource<WeatherResponseDto> {

		return try {
			Resource.Loading<WeatherResponseDto>()

			val weatherData = repository.fetchTodayWeather(location)
			Resource.Success(weatherData)
		} catch (e: Exception) {
			e.printStackTrace()
			when (e) {
				is IOException -> Resource.Error("No internet.")
				is HttpException -> Resource.Error("Could not reach server. Please retry.")

				else -> Resource.Error(e.localizedMessage!!)
			}
		}

	}
}