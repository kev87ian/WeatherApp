package com.kev.weatherapp.presentation.current_weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.kev.weatherapp.R
import com.kev.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.kev.weatherapp.domain.model.WeatherDomainModel
import com.kev.weatherapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint


// The request code used in the request for location permissions
private const val LOCATION_PERMISSION_REQUEST_CODE = 1

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {
	private var _binding: FragmentCurrentWeatherBinding? = null
	private val binding get() = _binding!!

	private val viewModel: CurrentWeatherViewModel by viewModels()

	// The FusedLocationProviderClient that provides location updates
	private lateinit var fusedLocationClient: FusedLocationProviderClient

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Initialize the FusedLocationProviderClient
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
	}


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
		return binding.root
	}
	override fun onStart() {
		super.onStart()
		// Check if the app has location permissions
		if (ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_FINE_LOCATION
			) != PackageManager.PERMISSION_GRANTED &&
			ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_COARSE_LOCATION
			) != PackageManager.PERMISSION_GRANTED
		) {

			// Request location permissions from the user
			requestPermissions(
				arrayOf(
					Manifest.permission.ACCESS_FINE_LOCATION,
					Manifest.permission.ACCESS_COARSE_LOCATION
				),
				LOCATION_PERMISSION_REQUEST_CODE
			)
		} else {
			// If the app already has location permissions, start getting the user's location
			startGettingLocation()
		}
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		updateUiState()
		startGettingLocation()
	}


	//updates the ui depending on the api's response
	private fun updateUiState() {



	viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { state ->

			when (state) {
				is Resource.Loading -> {
					binding.progressBar.visibility = View.VISIBLE

					binding.errorTextview.visibility = View.GONE
					}
				is Resource.Success -> {

					binding.progressBar.visibility = View.GONE
					binding.views.visibility = View.VISIBLE
					Toast.makeText(requireContext(), state.data?.location?.name.toString(), Toast.LENGTH_SHORT).show()
					state.data.apply {
						bindUi(this!!)
					}
				}

				is Resource.Error -> {
					binding.progressBar.visibility = View.GONE
					binding.errorTextview.visibility = View.VISIBLE
					binding.errorTextview.text = state.message
				}


			}

		}

	}

	private fun bindUi(weatherDomainModel: WeatherDomainModel) {

		binding.locationNameTextview.text = weatherDomainModel.location?.name
		binding.textviewFeelsLike.text = "Feels like ".plus(weatherDomainModel.current?.feelsLikeC)
	}


	// gets user's location, and makes the api call with the values
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
			.addOnCompleteListener { task ->
				val location = task.result


				//handling cases where the location values are null

				if (location != null) {
					viewModel.getCurrentWeather(
						location.latitude.toString().plus(",").plus(location.longitude.toString())
					)
				} else {
					Toast.makeText(
						requireContext(),
						"Couldn't get your location. Please retry",
						Toast.LENGTH_SHORT
					).show()
				}


			}

			.addOnFailureListener {
				Log.e("Location error", it.message!!)
				Toast.makeText(requireContext(), it.localizedMessage!!, Toast.LENGTH_SHORT).show()
			}


	}



	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<String>,
		grantResults: IntArray
	) {
		when (requestCode) {
			LOCATION_PERMISSION_REQUEST_CODE -> {
				// If the permissions were granted, start getting the user's location
				if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					startGettingLocation()
				} else {
					Toast.makeText(
						requireContext(),
						"App won't work without the permissions",
						Toast.LENGTH_SHORT
					).show()
				}
			}
		}
	}




	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}