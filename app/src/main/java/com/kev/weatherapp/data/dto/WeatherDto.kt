package com.kev.weatherapp.data.dto


import com.google.gson.annotations.SerializedName
import com.kev.weatherapp.domain.model.WeatherDomainModel

data class WeatherDto(
    @SerializedName("current")
    val current: CurrentWeatherDto,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)

fun WeatherDto.toWeatherDomainModel() : WeatherDomainModel{
    return WeatherDomainModel(
        current =  current.toCurrentWeatherDomainModel(),
        forecast = forecast,
        location = location

    )
}



