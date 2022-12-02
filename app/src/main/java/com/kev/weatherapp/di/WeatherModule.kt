package com.kev.weatherapp.di

import com.kev.weatherapp.data.remote.WeatherApiService
import com.kev.weatherapp.data.repository.WeatherRepositoryImpl
import com.kev.weatherapp.domain.repository.WeatherRepository
import com.kev.weatherapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

	@Provides
	@Singleton
	fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
		level = HttpLoggingInterceptor.Level.BODY
	}

	@Provides
	@Singleton
	fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
		return OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build()
	}

	@Provides
	@Singleton
	fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.baseUrl(Constants.BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

	@Provides
	@Singleton
	fun providesWeatherAPiService(retrofit: Retrofit): WeatherApiService {
		return retrofit.create(WeatherApiService::class.java)
	}


	@Provides
	@Singleton
	fun providesWeatherRepository(apiService: WeatherApiService) : WeatherRepository{
		return WeatherRepositoryImpl(apiService)
	}

}
