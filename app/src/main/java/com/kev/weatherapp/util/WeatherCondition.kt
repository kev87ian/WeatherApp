package com.kev.weatherapp.util

import androidx.annotation.DrawableRes
import com.kev.weatherapp.R

sealed class WeatherCondition(
	val weatherDesc: String,
	@DrawableRes val iconRes: Int
) {
	object ClearSky : WeatherCondition(
		weatherDesc = "Clear Sky",
		iconRes = R.drawable.sunny
	)

	object PartlyCloudy : WeatherCondition(
		weatherDesc = "Partly Cloudy",
		iconRes = R.drawable.ic_sunnycloudy
	)

	object Cloudy : WeatherCondition(
		weatherDesc = "Cloudy",
		iconRes = R.drawable.ic_cloudy
	)

	object Overcast : WeatherCondition(
		weatherDesc = "Overcast",
		iconRes = R.drawable.ic_very_cloudy
	)


	object Mist : WeatherCondition(
		weatherDesc = "Mist",
		iconRes = R.drawable.ic_rainshower
	)

	object HeavyRain : WeatherCondition(
		weatherDesc = "Heavy Rain",
		iconRes = R.drawable.ic_rainythunder
	)

	object LightDrizzle : WeatherCondition(
		weatherDesc = "Light Drizzle",
		iconRes = R.drawable.ic_rainy
	)

	object LightRain : WeatherCondition(
		weatherDesc = "Light Rain",
		iconRes = R.drawable.ic_rainshower
	)


	companion object {

		fun weatherPrediction(code: Int): WeatherCondition {
			return when (code) {
				1000 -> ClearSky
				1003 -> PartlyCloudy
				1006 -> Cloudy
				1009 -> Overcast
				1030 -> Mist
				1153 -> LightDrizzle
				1183 -> LightRain
				1195 -> HeavyRain

				else -> ClearSky
			}
		}

	}

}

