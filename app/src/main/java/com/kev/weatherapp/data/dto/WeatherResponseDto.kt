package com.kev.weatherapp.data.dto


import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("current")
    val currentDto: CurrentDto,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)