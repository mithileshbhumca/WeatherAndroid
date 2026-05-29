package com.example.weatherforecast.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.databinding.CityItemBinding

class CityAdapter(
    private val onItemClick: (City) -> Unit
) : ListAdapter<City, CityAdapter.CityViewHolder>(CityDiffCallback()) {

    inner class CityViewHolder(private val binding: CityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) = with(binding) {
            cityNameTxt.text = city.name
            stateNameTxt.text = city.state
            countryNameTxt.text = city.country
            itemView.setOnClickListener { onItemClick(city) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = CityItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CityViewHolder(binding)
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
