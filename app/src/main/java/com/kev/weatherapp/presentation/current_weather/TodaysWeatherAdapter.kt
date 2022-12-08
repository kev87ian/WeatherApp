package com.kev.weatherapp.presentation.current_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kev.weatherapp.R
import com.kev.weatherapp.data.dto.Hour
import com.kev.weatherapp.databinding.TodayForecastLayoutItemBinding
import com.kev.weatherapp.util.WeatherCondition
import java.text.ParseException
import java.text.SimpleDateFormat
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

		var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
		var date: Date? = null
		try {
			date = formatter.parse(singleHour.time)
		} catch (e: ParseException) {
			// TODO Auto-generated catch block
			e.printStackTrace()
		}
		formatter = SimpleDateFormat("dd-MMM-yyyy HH:mm")

		//removing the backslash that precede the icon url
		var url = singleHour.condition.icon
		val str2 = "//"
		val result = url.startsWith(str2)


		with(holder) {
			binding.apply {

				val x = WeatherCondition.weatherPrediction(singleHour.condition.code)
				hourTv.text = singleHour.time
				temperatureTextview.text = singleHour.tempC.toString().plus("°")


				x.let {
					condtionTextview.text = it.weatherDesc
					imageview.setImageResource(x.iconRes)
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


