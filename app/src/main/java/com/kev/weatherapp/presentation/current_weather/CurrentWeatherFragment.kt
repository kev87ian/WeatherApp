package com.kev.weatherapp.presentation.current_weather

/*


// The request code used in the request for location permissions
private const val LOCATION_PERMISSION_REQUEST_CODE = 1

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {
	private var _binding: FragmentCurrentWeatherBinding? = null
	private val binding get() = _binding!!

	private val viewModel: CurrentWeatherViewModel by viewModels()

	private lateinit var todayForecastAdapter: TodayWeatherForecastAdapter

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



	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//init adapter
		todayForecastAdapter = TodayWeatherForecastAdapter()

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
					state.data.apply {
						bindUi(this!!)
					*/
/*	get24HourWeatherForecast(this.forecast!!)*//*

					//get24HourWeatherForecast(state.data?.forecast!!)

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

	private fun get24HourWeatherForecast(forecast: Forecast) {

		binding.recycleview.apply {
			adapter = todayForecastAdapter
			layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		}

		viewModel.currentWeatherLiveData.observe(viewLifecycleOwner){state->
			when(state){
				is Resource.Loading->{

				}

				is Resource.Success->{

					todayForecastAdapter.differ.submitList(state.data?.forecast?.forecastday)

				}
			}

		}



	}


	//the 24 hour weather forecast


	//binds the Ui response to views
	@SuppressLint("SimpleDateFormat")
	private fun bindUi(weatherDomainModel: WeatherDomainModel) {


		var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
		var date: Date? = null
		try {
			date = formatter.parse(weatherDomainModel.current?.lastUpdate.toString())
		} catch (e: ParseException) {
			// TODO Auto-generated catch block
			e.printStackTrace()
		}
		formatter = SimpleDateFormat("dd-MMM-yyyy HH:mm")

		//	System.out.println("Date :" + formatter.format(date!!))


		binding.apply {
			locationNameTextview.text = weatherDomainModel.location?.name
			textviewFeelsLike.text = "Feels like ".plus(weatherDomainModel.current?.feelsLikeC).plus("\u00B0")
			conditionText.text = weatherDomainModel.current?.condition?.text
			windspeedTextview.text = weatherDomainModel.current?.windKph.toString().plus("km/h")
			timeLastUpdated.text = "Last update: ".plus(formatter.format(date!!))
			temperatureTextview.text = weatherDomainModel.current?.tempC.toString().plus("\u00B0")
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


				//handling cases where the location values are null

				if (location != null) {
					viewModel.getCurrentWeather(
						location.latitude.toString().plus(",").plus(location.longitude.toString())
					)
				} else {
					Toast.makeText(
						requireContext(),
						"Couldn't get your location. Please ensure you have granted location permission.",
						Toast.LENGTH_SHORT
					).show()
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
}*/
