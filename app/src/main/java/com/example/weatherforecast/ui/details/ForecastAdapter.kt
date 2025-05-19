package com.example.weatherforecast.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecast.R
import com.example.weatherforecast.data.model.ThreeHoursWeatherForecast
import com.example.weatherforecast.databinding.ItemForecastBinding
import com.example.weatherforecast.utils.Constants
import com.example.weatherforecast.utils.FormattingUtil
import kotlin.math.roundToInt

class ForecastAdapter :
    ListAdapter<ThreeHoursWeatherForecast, ForecastAdapter.ForecastViewHolder>(ForecastDiffCallback()) {
    inner class ForecastViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ThreeHoursWeatherForecast?) = with(binding) {
            dateText.text = FormattingUtil.getDateFormatEEE(item?.dt)
            maxTemp.text = itemView.context.getString(
                R.string.max_c, item?.main?.tempMax?.roundToInt()
            )
            minTemp.text = itemView.context.getString(
                R.string.min_c, item?.main?.tempMin?.roundToInt()
            )
            val icon = item?.weather?.firstOrNull()?.icon.orEmpty()
            val iconUrl = "${Constants.ICON_URL}$icon.png"

            Glide.with(itemView.context)
                .load(iconUrl)
                .into(weatherIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ForecastDiffCallback : DiffUtil.ItemCallback<ThreeHoursWeatherForecast>() {
        override fun areItemsTheSame(
            oldItem: ThreeHoursWeatherForecast,
            newItem: ThreeHoursWeatherForecast
        ): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(
            oldItem: ThreeHoursWeatherForecast,
            newItem: ThreeHoursWeatherForecast
        ): Boolean {
            return oldItem == newItem
        }
    }

}