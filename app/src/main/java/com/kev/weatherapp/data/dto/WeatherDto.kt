package com.kev.weatherapp.data.dto


import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("current")
    val current: CurrentWeatherDto,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)