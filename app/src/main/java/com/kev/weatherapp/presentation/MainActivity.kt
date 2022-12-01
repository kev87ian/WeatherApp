package com.kev.weatherapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.BuildConfig
import com.kev.weatherapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

	}
}