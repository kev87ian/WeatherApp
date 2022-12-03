package com.kev.weatherapp.presentation.current_weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kev.weatherapp.R
import com.kev.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.kev.weatherapp.domain.model.CurrentWeatherDomainModel
import com.kev.weatherapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

	private var _binding: FragmentCurrentWeatherBinding? = null
	private val binding get() = _binding!!

	private val viewModel: CurrentWeatherViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		fetchWeather()
	}

	private fun fetchWeather() {

		val locationError = viewModel.locationPermissionObservable.value

		viewModel.fetchCurrentWeather()


		//check permission-related errors
		if (locationError.isNullOrEmpty()) {
			viewModel.fetchCurrentWeather()
			viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { result ->

				when (result) {
					is Resource.Loading -> {
						Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
					}

					is Resource.Error -> {
						Toast.makeText(
							requireContext(),
							result.message.toString(),
							Toast.LENGTH_SHORT
						).show()
					}

					is Resource.Success -> {
						Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
						bindUi(result.data!!)
					}
				}
			}

		}else{
			Toast.makeText(requireContext(), "No internet permission", Toast.LENGTH_SHORT).show()
		}
	}

	private fun bindUi(data: CurrentWeatherDomainModel) {

		val temperature = data.tempC
		Toast.makeText(requireContext(), temperature.toString(), Toast.LENGTH_SHORT).show()
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}