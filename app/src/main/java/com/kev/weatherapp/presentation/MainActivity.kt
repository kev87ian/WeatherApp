package com.kev.weatherapp.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.kev.weatherapp.R
import com.kev.weatherapp.presentation.current_weather.CurrentWeatherFragment
import com.kev.weatherapp.presentation.current_weather.CurrentWeatherFragmentDirections
import com.kev.weatherapp.presentation.current_weather.CurrentWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var navController: NavController
	private lateinit var fusedLocationClient: FusedLocationProviderClient
	private val viewModel: CurrentWeatherViewModel by viewModels()

	@RequiresApi(Build.VERSION_CODES.S)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

		val navHostFragment = supportFragmentManager.findFragmentById(R.id.weatherNavHostFragment) as NavHostFragment
		navController = navHostFragment.navController


		getCurrentLocation()



	}


	@RequiresApi(Build.VERSION_CODES.S)
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

						val location = location.latitude.toString().plus(location.longitude.toString())
						val action = CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentSelf(location)

					}


					.addOnFailureListener { exception ->
						Log.d("Location", "Oops location failed with exception: $exception")
					}

			} else {
				//open esttings here
				Toast.makeText(this@MainActivity, "Turn on on gps services", Toast.LENGTH_SHORT)
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
			4
		)

	}


	@RequiresApi(Build.VERSION_CODES.S)
	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == 4) {
			if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(this@MainActivity, "Successfully granted", Toast.LENGTH_SHORT).show()

				supportFragmentManager.beginTransaction()
					.commitNow()

			} else {
				Toast.makeText(this@MainActivity, "Permissions denied.", Toast.LENGTH_SHORT).show()
				finish()
			}


		}

	}


}