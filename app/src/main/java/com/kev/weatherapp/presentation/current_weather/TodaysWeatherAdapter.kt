package com.kev.weatherapp.presentation.current_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kev.weatherapp.data.dto.Hour
import com.kev.weatherapp.databinding.TodayForecastLayoutItemBinding
import com.kev.weatherapp.util.WeatherCondition

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

		val listOfHours = differ.currentList

		//val listOfHours = listOf<Hour>()
		with(holder) {

			binding.apply {

				for (hour in listOfHours) {
					hourTv.text = hour.time
					temperatureTextview.text = hour.tempC.toString()
					imageview.setImageDrawable(WeatherCondition.weatherPrediction(hour.condition.code).iconRes.toDrawable())

				}
			}
		}

	}


	override fun getItemCount(): Int {
		return differ.currentList.size
	}

/*	private val diffUtil = object : DiffUtil.ItemCallback<Forecastday>() {
		override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
			return oldItem.date == newItem.date
		}

		override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
			return oldItem == newItem
		}
	}*/

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


