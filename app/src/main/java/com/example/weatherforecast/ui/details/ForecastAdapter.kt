package com.example.weatherforecast.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecast.R
import com.example.weatherforecast.data.model.ThreeHoursWeatherForecast
import com.example.weatherforecast.databinding.ItemForecastBinding
import com.example.weatherforecast.utils.Constants
import com.example.weatherforecast.utils.FormattingUtil
import kotlin.math.roundToInt

class ForecastAdapter(private val forecastList: List<ThreeHoursWeatherForecast>?) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ForecastViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = forecastList?.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return forecastList?.size ?: 0
    }

    class ForecastViewHolder(binding: ItemForecastBinding, private val mContext: Context) :
        RecyclerView.ViewHolder(binding.root) {
        private var dateText: TextView = binding.dateText
        private var maxTempTextView: TextView = binding.maxTemp
        private var minTempTextView: TextView = binding.minTemp
        private var weatherIcon: ImageView = binding.weatherIcon
        fun bind(item: ThreeHoursWeatherForecast?) {
            dateText.text = FormattingUtil.getDateFormatEEE(item?.dt)
            maxTempTextView.text =
                String.format(mContext.getString(R.string.max_c), item?.main?.tempMax?.roundToInt())
            minTempTextView.text =
                String.format(mContext.getString(R.string.min_c), item?.main?.tempMin?.roundToInt())

            val iconUrl = "${Constants.ICON_URL}${item?.weather!![0].icon}.png"

            Glide.with(itemView.context)
                .load(iconUrl)
                .into(weatherIcon)
        }
    }


}