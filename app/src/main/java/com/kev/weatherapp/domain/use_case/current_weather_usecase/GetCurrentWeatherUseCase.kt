package com.kev.weatherapp.domain.use_case.current_weather_usecase

import com.kev.weatherapp.data.dto.CurrentWeatherDto
import com.kev.weatherapp.data.dto.toWeatherDomainModel
import com.kev.weatherapp.domain.model.WeatherDomainModel
import com.kev.weatherapp.domain.repository.WeatherRepository
import com.kev.weatherapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
	private val repository: WeatherRepository
) {

	suspend fun fetchCurrentWeather(location: String): Flow<com.kev.weatherapp.util.Resource<WeatherDomainModel>> =
		flow {

			emit(Resource.Loading())
			val currentWeatherData =
				repository.fetchCurrentWeatherDetails(location).current.toWeatherDomainModel()
			emit(Resource.Success(currentWeatherData))


		}.flowOn(Dispatchers.IO).catch { e ->
			e.printStackTrace()
			when(e){
				is IOException-> emit(Resource.Error("Hauna net"))
				is HttpException-> emit(Resource.Error("Having trouble reaching server"))
				else -> emit(Resource.Error(e.localizedMessage!!))
			}

		}


}