package com.example.weatherforecast.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherforecast.R
import com.example.weatherforecast.data.model.ThreeHoursWeatherForecast
import com.example.weatherforecast.data.model.WeatherDetailData
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.databinding.FragmentWeatherDetailBinding
import com.example.weatherforecast.domain.repository.UiState
import com.example.weatherforecast.utils.Constants
import com.example.weatherforecast.utils.Constants.ICON_URL
import com.example.weatherforecast.utils.FormattingUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private lateinit var binding: FragmentWeatherDetailBinding

    private val detailViewModel: WeatherDetailViewModel by viewModels()
    private lateinit var forecastAdapter: ForecastAdapter

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
        arguments?.let {
            val cityLat = it.getFloat(Constants.CITY_LAT, 0f).toDouble()
            val cityLog = it.getFloat(Constants.CITY_LOG, 0f).toDouble()
            if (cityLat != 0.0 && cityLog != 0.0) {
                detailViewModel.fetchDetails(cityLat, cityLog)
            }
        }
        return binding.root
    }

    private fun setupUI() {
        forecastAdapter = ForecastAdapter()
        binding.forecastRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = forecastAdapter
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.forecastRecyclerView.visibility = View.GONE
                        }

                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderData(uiState.data)
                            binding.forecastRecyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_LONG)
                                .show()
                        }

                        else -> {
                            //nothing
                        }
                    }
                }
            }
        }
    }


    private fun renderData(weatherDetailData: WeatherDetailData) = with(binding) {
        val cityWeather = weatherDetailData.currentWeather
        val cityForeCast = weatherDetailData.weatherForecast
        location.text = getString(R.string.location, cityWeather.name, cityWeather.sys?.country)
        cloudDesc.text = cityWeather.weather?.get(0)?.description
        locTime.text = FormattingUtil.getDateFormatEEE(cityWeather.dt)
        temperature.text =
            getString(R.string._1f_ctemp, cityWeather.main?.temp?.roundToInt())
        maxTemp.text =
            getString(R.string.temp_max, cityWeather.main?.tempMax?.roundToInt())
        minTemp.text =
            getString(R.string.temp_min, cityWeather.main?.tempMin?.roundToInt())
        val iconUrl = cityWeather.weather?.firstOrNull()?.icon?.let { "$ICON_URL$it.png" }.orEmpty()
        Glide.with(this@WeatherDetailFragment)
            .load(iconUrl.ifEmpty { R.drawable.ic_broken_image })
            .into(weatherIcon)
        renderForecast(cityForeCast)
    }

    private fun renderForecast(cityForeCast: WeatherForecast) {
        cityForeCast.list?.let {
            val forecastList = getUniqueForecasts(it)
            forecastAdapter.submitList(forecastList)
        }
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
