package com.kev.weatherapp.presentation.current_weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.kev.weatherapp.R
import com.kev.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.kev.weatherapp.domain.model.CurrentWeatherDomainModel
import com.kev.weatherapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint



// The request code used in the request for location permissions
private const val LOCATION_PERMISSION_REQUEST_CODE = 1

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {
	private var _binding : FragmentCurrentWeatherBinding? = null
	private val binding get() = _binding!!

	private val viewModel:CurrentWeatherViewModel by viewModels()
	// The FusedLocationProviderClient that provides location updates
	private lateinit var fusedLocationClient: FusedLocationProviderClient

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Initialize the FusedLocationProviderClient
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
	}


	override fun onStart() {
		super.onStart()
		// Check if the app has location permissions
		if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
			ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			// Request location permissions from the user
			requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
				LOCATION_PERMISSION_REQUEST_CODE)
		} else {
			// If the app already has location permissions, start getting the user's location
			startGettingLocation()
		}
	}


	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		when (requestCode) {
			LOCATION_PERMISSION_REQUEST_CODE -> {
				// If the permissions were granted, start getting the user's location
				if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					startGettingLocation()
				}

				else{
					Toast.makeText(requireContext(), "App won't work without the permissions", Toast.LENGTH_SHORT).show()
				}
			}
		}
	}



	private fun observeUiState(){

		viewModel.currentWeatherLiveData.observe(viewLifecycleOwner){state->

			when(state){
				is Resource.Success->{

					state.data.apply {
						bindUi(this)
					}
				}

				is Resource.Error->{

				}

				is Resource.Loading->{
					Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
				}
			}

		}

	}

	//bind the ui with the api object
	private fun bindUi(currentWeatherDomainModel: CurrentWeatherDomainModel?) {

	}

	// Start getting the user's location
	private fun startGettingLocation() {

		val priority = LocationRequest.QUALITY_BALANCED_POWER_ACCURACY
		val cancellationTokenSource = CancellationTokenSource()


		if (ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_FINE_LOCATION
			) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				requireContext(),
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
			return
		}
		fusedLocationClient.getCurrentLocation(priority, cancellationTokenSource.token)
			.addOnCompleteListener{task->
				val location = task.result

			viewModel.getCurrentWeather(location.latitude.toString().plus(",").plus(location.longitude.toString()))

			}

			.addOnFailureListener {
				Log.e("Location error", it.message!!)
				Toast.makeText(requireContext(), "Couldn't get your location. Please retry", Toast.LENGTH_SHORT).show()
			}



	}

		override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}