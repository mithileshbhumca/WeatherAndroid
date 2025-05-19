package com.example.weatherforecast.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.data.model.City

class CityAdapter(
    private val onItemClick: (City) -> Unit
) : ListAdapter<City, CityAdapter.CityViewHolder>(CityDiffCallback()) {

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cityName: TextView = itemView.findViewById(R.id.city_name_txt)
        private val stateName: TextView = itemView.findViewById(R.id.state_name_txt)
        private val countryName: TextView = itemView.findViewById(R.id.country_name_txt)
        fun bind(city: City) {
            cityName.text = city.name
            stateName.text = city.state
            countryName.text = city.country
            itemView.setOnClickListener { onItemClick(city) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)

        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CityDiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            // If City has a unique ID, use it. Otherwise compare lat/lon or name
            //return oldItem.lat == newItem.lat && oldItem.lon == newItem.lon
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }
}
