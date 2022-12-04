package com.kev.weatherapp.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.kev.weatherapp.R
import com.kev.weatherapp.presentation.current_weather.CurrentWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var navController: NavController
	private lateinit var fusedLocationClient: FusedLocationProviderClient
	private val viewModel: CurrentWeatherViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

		val navHostFragment = supportFragmentManager.findFragmentById(R.id.weatherNavHostFragment) as NavHostFragment
		navController = navHostFragment.navController

		getCurrentLocation()
	}


	companion object {
		private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100

		//final latitude and longitude
		var longitude: String? = null
		var latitude: String? = null

	}


	private fun getCurrentLocation() {

		if (checkLocationPermissions()) {

			//if permissions are given but gps disabled
			if (checkIfLocationIsEnabled()) {

				//getting final lattitude and longitude values

				if (ActivityCompat.checkSelfPermission(
						this@MainActivity,
						Manifest.permission.ACCESS_FINE_LOCATION
					) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
						this@MainActivity,
						Manifest.permission.ACCESS_COARSE_LOCATION
					) != PackageManager.PERMISSION_GRANTED
				) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					checkLocationPermissions()
					return
				}


				val priority = LocationRequest.QUALITY_BALANCED_POWER_ACCURACY
				val cancellationTokenSource = CancellationTokenSource()

				fusedLocationClient.getCurrentLocation(priority, cancellationTokenSource.token)
					.addOnSuccessListener { location ->
						Log.d("Location", "location is found: ${location.time}")
						longitude = location.longitude.toString()
						latitude = location.latitude.toString()
					}



					.addOnFailureListener { exception ->
						Log.d("Location", "Oops location failed with exception: $exception")
					}

			} else {
				//open esttings here
				Toast.makeText(this@MainActivity, "Turon on gps services", Toast.LENGTH_SHORT)
					.show()
				val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
				startActivity(intent)
			}

		} else {
//request permissions
			requestPermission()
		}
	}

	private fun checkIfLocationIsEnabled(): Boolean {
		val locationManager: LocationManager =
			this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
		return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
			LocationManager.GPS_PROVIDER
		)
	}

	private fun checkLocationPermissions(): Boolean {

		if (ActivityCompat.checkSelfPermission(
				this@MainActivity,
				Manifest.permission.ACCESS_COARSE_LOCATION
			) == PackageManager.PERMISSION_GRANTED &&
			ActivityCompat.checkSelfPermission(
				this@MainActivity,
				Manifest.permission.ACCESS_FINE_LOCATION
			) == PackageManager.PERMISSION_GRANTED
		) {

			return true
		}

		return false
	}

	private fun requestPermission() {

		ActivityCompat.requestPermissions(
			this@MainActivity, arrayOf(
				Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.ACCESS_COARSE_LOCATION
			),
			PERMISSION_REQUEST_ACCESS_LOCATION
		)

	}


	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
			if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(this@MainActivity, "Successfully granted", Toast.LENGTH_SHORT).show()
				getCurrentLocation()
			} else {
				Toast.makeText(this@MainActivity, "Denied", Toast.LENGTH_SHORT).show()
			}


		}

	}


}