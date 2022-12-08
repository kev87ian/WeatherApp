package com.kev.weatherapp.presentation.current_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kev.weatherapp.data.dto.Hour
import com.kev.weatherapp.databinding.TodayForecastLayoutItemBinding
import com.kev.weatherapp.util.WeatherCondition
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class TodaysWeatherAdapter : RecyclerView.Adapter<TodaysWeatherAdapter.WeatherViewHolder>() {

	class WeatherViewHolder(val binding: TodayForecastLayoutItemBinding) :
		RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {

		val binding = TodayForecastLayoutItemBinding.inflate(
			LayoutInflater.from(parent.context), parent, false
		)

		return WeatherViewHolder(binding)
	}

	override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {

		val singleHour = differ.currentList[position]


		with(holder) {
			binding.apply {

				val condition = WeatherCondition.weatherPrediction(singleHour.condition.code)
				hourTv.text = singleHour.time
				temperatureTextview.text = "Feels like ".plus(singleHour.tempC.toString().plus("°"))


				condition.let {
					condtionTextview.text = it.weatherDesc
					imageview.setImageResource(condition.iconRes)
				}



			}


		}
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	private val diffUtil = object : DiffUtil.ItemCallback<Hour>() {
		override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean {
			return oldItem.time == newItem.time
		}

		override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean {
			return oldItem == newItem
		}
	}


	val differ = AsyncListDiffer(this, diffUtil)


}


