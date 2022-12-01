package com.kev.weatherapp.data.dto


import com.google.gson.annotations.SerializedName

data class ForeCastDay(
    @SerializedName("astro")
    val astro: Astro,
    @SerializedName("date")
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    @SerializedName("day")
    val day: Day,
    @SerializedName("hour")
    val hourDto: List<HourDto>
)