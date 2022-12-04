package com.kev.weatherapp.presentation.current_weather

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kev.weatherapp.R
import com.kev.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.kev.weatherapp.presentation.MainActivity
import com.kev.weatherapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

	private var _binding: FragmentCurrentWeatherBinding? = null
	private val binding get() = _binding!!

	private val viewModel: CurrentWeatherViewModel by viewModels()


	private lateinit var mainActivity: MainActivity


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		mainActivity = context as MainActivity


	}
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)



		observerUi()




		//fetch weather update


	}





	private fun observerUi() {


		viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) { state ->

			when (state) {
				is Resource.Loading -> {
					Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
				}

				is Resource.Success -> {
					//TODO
					Log.d("network calll success", state.data.toString())
				}

				is Resource.Error -> {
					Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
				}
			}

		}
	}


	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}