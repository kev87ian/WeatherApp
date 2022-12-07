package com.kev.weatherapp.presentation.current_weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.kev.weatherapp.R
import com.kev.weatherapp.data.dto.CurrentDto
import com.kev.weatherapp.databinding.FragmentCurrentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


// The request code used in the request for location permissions
private const val LOCATION_PERMISSION_REQUEST_CODE = 1

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {
	private var _binding: FragmentCurrentWeatherBinding? = null
	private val binding get() = _binding!!

	// The FusedLocationProviderClient that provides location updates
	private lateinit var fusedLocationClient: FusedLocationProviderClient

	private val viewModel: TodaysWeatherViewModel by viewModels()


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
		startGettingLocation()
		observeDataChanges()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
		return binding.root
	}

	private fun observeDataChanges() {
		viewModel.dataSTate.observe(viewLifecycleOwner) { state ->

			if (state.isLoading) {
				//progress bar is visible
				Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
			} else if (state.error != null) {

				binding.errorTextview.visibility = View.VISIBLE
				binding.errorTextview.text = state.error.toString()
				binding.progressBar.visibility = View.GONE
				Toast.makeText(requireContext(), state.error.toString(), Toast.LENGTH_SHORT).show()
			} else if (state.weatherInfo != null) {

				binding.errorTextview.visibility = View.GONE
				binding.progressBar.visibility = View.GONE
				state.weatherInfo.currentDto.let {
					bindUi(it)
				}
			}

		}
	}

	@SuppressLint("SimpleDateFormat")
	private fun bindUi(currentDto: CurrentDto) {
		//handling time conversion
		var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
		var date: Date? = null
		try {
			date = formatter.parse(currentDto.lastUpdated)
		} catch (e: ParseException) {
			// TODO Auto-generated catch block
			e.printStackTrace()
		}
		formatter = SimpleDateFormat("dd-MMM-yyyy HH:mm")
		binding.apply {
			temperatureTextview.text = currentDto.tempC.toString().plus("\u00B0")
			textviewFeelsLike.text = "Feels like ".plus(currentDto.feelslikeC.toString().plus("\u00B0"))
			timeLastUpdated.text = "Last updated: ".plus(formatter.format(date!!))
			windspeedTextview.text = currentDto.windKph.toString().plus("km/h")
			conditionText.text = currentDto.condition.text

		}
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

				if (location != null) {
					viewModel.getTodaysWeather(
						location.latitude.toString().plus(",").plus(location.longitude)
					)
				} else {

					//user has granted permissions, but has not turned on the location/gps feature
					Toast.makeText(
						requireContext(),
						"Couldn't get your location. Please ensure you have granted location permission.",
						Toast.LENGTH_SHORT
					).show()

					startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))

				}

			}

			.addOnFailureListener {
				Log.e("Location error", it.message!!)
				Toast.makeText(requireContext(), it.localizedMessage!!, Toast.LENGTH_SHORT).show()
			}


	}


	@Deprecated("Deprecated in Java")
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


	@RequiresApi(Build.VERSION_CODES.S)
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


	override fun onDestroy() {
		super.onDestroy()
		_binding = null

	}
}