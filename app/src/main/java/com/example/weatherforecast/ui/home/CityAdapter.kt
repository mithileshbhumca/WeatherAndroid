package com.example.weatherforecast.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.data.model.City

class CityAdapter(
    private val cities: ArrayList<City>,
    private val onItemClick: (City) -> Unit
) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityName: TextView = itemView.findViewById(R.id.city_name_txt)
        val stateName: TextView = itemView.findViewById(R.id.state_name_txt)
        val countryName: TextView = itemView.findViewById(R.id.country_name_txt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)

        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.cityName.text = city.name
        holder.stateName.text = city.state
        holder.countryName.text = city.country
        holder.itemView.setOnClickListener {
            onItemClick(city)
        }
    }

    override fun getItemCount(): Int = cities.size


    fun addData(list: List<City>) {
        cities.clear()
        cities.addAll(list)
    }
}
