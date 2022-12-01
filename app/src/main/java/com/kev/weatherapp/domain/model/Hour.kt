package com.kev.weatherapp.domain.model

import com.kev.weatherapp.data.dto.Condition

data class Hour (
val time:String,
val tempC : Double,
val condition: Condition,
val humidity:Int,
val windKph: Double,
val feelsLikeC:Double
		)