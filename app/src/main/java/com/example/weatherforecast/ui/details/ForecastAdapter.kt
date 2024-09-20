package com.example.weatherforecast.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecast.R
import com.example.weatherforecast.data.model.ThreeHoursWeatherForecast
import java.text.SimpleDateFormat
import java.util.Locale

class ForecastAdapter(private val forecastList: List<ThreeHoursWeatherForecast>?) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = forecastList?.get(position)
        holder.dateText.text = SimpleDateFormat("EEE, d MMM", Locale.getDefault())
            .format(item!!.dt!! * 1000)

        holder.maxTempTextView.text = "Max: ${item.main?.tempMax}°C"
        holder.minTempTextView.text = "Min: ${item.main?.tempMin}°C"

        // Load the weather icon (assuming `item.weather[0].icon` is the icon code)
        val iconUrl = "https://openweathermap.org/img/wn/${item.weather!![0].icon}@2x.png"
//        Glide.with(holder.itemView.context)
//            .load(iconUrl)
//            .into(holder.weatherIcon)

    }

    override fun getItemCount(): Int {
        return forecastList?.size ?: 0
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateText: TextView = itemView.findViewById(R.id.dateText)
        val maxTempTextView: TextView = itemView.findViewById(R.id.max_temp)
        val minTempTextView: TextView = itemView.findViewById(R.id.min_temp)
        //val weatherIcon: ImageView = itemView.findViewById(R.id.weatherIcon)
    }
}