package com.kev.weatherapp.presentation.current_weather

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kev.weatherapp.R
import com.kev.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.kev.weatherapp.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

	private var _binding: FragmentCurrentWeatherBinding? = null
	private val binding get() = _binding!!

	private val viewModel: CurrentWeatherViewModel by viewModels()

	private val args: CurrentWeatherFragmentArgs by navArgs()
	private lateinit var mainActivity: MainActivity

	private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {


		_binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
		return binding.root

	}


	@RequiresApi(Build.VERSION_CODES.S)
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fusedLocationProviderClient =
			LocationServices.getFusedLocationProviderClient(requireActivity())
/*

		if (mainActivity.getCurrentLocation()){

			Toast.makeText(requireContext(), "Inaleta logins", Toast.LENGTH_SHORT).show()
		}else{

		}
*/

/*
		getCurrentLocation()


		dataStateUiObserver()
*/


	}
/*

	private fun getCurrentLocation() {

		Log.d("Function imeitwa", "Function Imeitwa")

		if (checkLocationPermissions()) {

			//if permissions are given but gps disabled
			if (checkIfLocationIsEnabled()) {

				//getting final lattitude and longitude values

				if (ActivityCompat.checkSelfPermission(
						requireContext(),
						Manifest.permission.ACCESS_FINE_LOCATION
					) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
						requireContext()
						,
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


				fusedLocationProviderClient.getCurrentLocation(priority, cancellationTokenSource.token)
					.addOnSuccessListener { location ->
						Log.d("Location", "location is found: ${location.time}")

						val location = location.latitude.toString().plus(location.longitude.toString())
						val action = CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentSelf(location)


						viewModel.getCurrentWeather(location)

					}




					.addOnFailureListener { exception ->
						Log.d("Location", "Oops location failed with exception: $exception")
					}

			} else {
				//open esttings here
				Toast.makeText(requireContext(), "Turn on on gps services", Toast.LENGTH_SHORT)
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
			requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
		return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
			LocationManager.GPS_PROVIDER
		)
	}



	private fun checkLocationPermissions(): Boolean {

		if (ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_COARSE_LOCATION
			) == PackageManager.PERMISSION_GRANTED &&
			ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_FINE_LOCATION
			) == PackageManager.PERMISSION_GRANTED
		) {

			return true
		}

		return false
	}

	private fun requestPermission() {

		ActivityCompat.requestPermissions(
			requireActivity(), arrayOf(
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
				Toast.makeText(requireContext(), "Successfully granted", Toast.LENGTH_SHORT).show()
				getCurrentLocation()
			} else {
				Toast.makeText(requireContext(), "Denied", Toast.LENGTH_SHORT).show()

			}


		}

	}



	private fun dataStateUiObserver() {



		viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { state ->

			when (state) {
				is Resource.Loading -> {
					Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
				}

				is Resource.Success -> {
					//TODO
					Log.d("network call success", state.data.toString())
				}

				is Resource.Error -> {
					Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
				}
			}

		}
	}
*/


	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}