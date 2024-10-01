package com.example.weatherforecast.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecast.R
import com.example.weatherforecast.domain.repository.UiState
import com.example.weatherforecast.data.model.ThreeHoursWeatherForecast
import com.example.weatherforecast.data.model.WeatherDetailData
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.databinding.FragmentWeatherDetailBinding
import com.example.weatherforecast.utils.Constants
import com.example.weatherforecast.utils.Constants.ICON_URL
import com.example.weatherforecast.utils.FormattingUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.LinkedHashMap
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private lateinit var binding: FragmentWeatherDetailBinding

    private val detailViewModel: WeatherDetailViewModel by viewModels()

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationText: TextView
    private lateinit var cloudDescText: TextView
    private lateinit var locationTimeText: TextView
    private lateinit var temperatureText: TextView
    private lateinit var maxTempText: TextView
    private lateinit var minTempText: TextView
    private lateinit var weatherIcon: ImageView

    private var forecastAdapter: ForecastAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This makes the back button available in the ActionBar
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWeatherDetailBinding.inflate(layoutInflater)
        setupUI()
        setupObserver()
        // Get city name from the Intent
        val cityLat = arguments?.getFloat(Constants.CITY_LAT, 0f)?.toDouble()
        val cityLog = arguments?.getFloat(Constants.CITY_LOG, 0f)?.toDouble()
        if (cityLat != 0.0 && cityLog != 0.0) {
            detailViewModel.fetchDetails(cityLat!!, cityLog!!)
        }
        return binding.root
    }

    private fun setupUI() {
        progressBar = binding.progressBar
        recyclerView = binding.forecastRecyclerView

        locationText = binding.location
        locationTimeText = binding.locTime
        cloudDescText = binding.cloudDesc
        temperatureText = binding.temperature
        maxTempText = binding.maxTemp
        minTempText = binding.minTemp
        weatherIcon = binding.weatherIcon

        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))

    }

    private fun setupObserver() {
        detailViewModel.getUiState().observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    progressBar.visibility = View.GONE
                    renderData(it.data)
                    recyclerView.visibility = View.VISIBLE
                }

                is UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

                is UiState.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderData(weatherDetailData: WeatherDetailData) {
        val cityWeather = weatherDetailData.currentWeather
        val cityForeCast = weatherDetailData.weatherForecast
        locationText.text = getString(R.string.location, cityWeather.name, cityWeather.sys?.country)
        cloudDescText.text = cityWeather.weather?.get(0)?.description
        locationTimeText.text = FormattingUtil.getDateFormatEEE(cityWeather.dt)
        temperatureText.text =
            String.format(getString(R.string._1f_ctemp), cityWeather.main?.temp?.roundToInt())
        maxTempText.text =
            String.format(getString(R.string.temp_max), cityWeather.main?.tempMax?.roundToInt())
        minTempText.text =
            String.format(getString(R.string.temp_min), cityWeather.main?.tempMin?.roundToInt())
        val icon = cityWeather.weather?.get(0)?.icon
        if (icon != null) {
            loadIcon("$ICON_URL${icon}.png")
        } else {
            loadIcon("")
        }

        renderForecast(cityForeCast)
    }


    private fun loadIcon(iconUrl: String) {
        if (iconUrl.isNotEmpty()) {
            Glide.with(this).load(iconUrl).into(weatherIcon)
        } else {
            Glide.with(this).load(R.drawable.ic_broken_image).into(weatherIcon)
        }
    }

    private fun renderForecast(cityForeCast: WeatherForecast) {
        val forecastList = getUniqueForecasts(cityForeCast.list!!)

        forecastAdapter = ForecastAdapter(forecastList)
        recyclerView.setAdapter(forecastAdapter)
    }

    private fun getUniqueForecasts(forecastList: List<ThreeHoursWeatherForecast>): List<ThreeHoursWeatherForecast> {
        val uniqueForecasts = ArrayList<ThreeHoursWeatherForecast>()
        val dateMap = LinkedHashMap<String?, ThreeHoursWeatherForecast>()

        for (item in forecastList) {
            val dateKey = FormattingUtil.getFormatDate(item.dt)
            if (!dateMap.containsKey(dateKey)) {
                dateMap[dateKey] = item
            }
        }

        uniqueForecasts.addAll(dateMap.values)
        return uniqueForecasts
    }

}
