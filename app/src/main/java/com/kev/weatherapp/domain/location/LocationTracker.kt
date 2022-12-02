package com.kev.weatherapp.domain.location

import android.location.Location

//respon
interface LocationTracker {
	suspend fun getCurrentLocation() : Location?
}