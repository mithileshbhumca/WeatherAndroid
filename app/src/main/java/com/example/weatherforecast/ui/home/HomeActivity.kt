package com.example.weatherforecast.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.data.api.ApiHelperImpl
import com.example.weatherforecast.data.api.UiState
import com.example.weatherforecast.data.api.ViewModelFactory
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.ui.details.WeatherDetailActivity
import com.example.weatherforecast.utils.Constants.CITY_LAT
import com.example.weatherforecast.utils.Constants.CITY_LOG
import me.amitshekhar.learn.kotlin.coroutines.data.api.RetrofitBuilder

class HomeActivity : ComponentActivity() {

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var adapter: CityAdapter

    private lateinit var citiesRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        searchView = findViewById(R.id.searchView)
        citiesRecyclerView = findViewById(R.id.citiesRecyclerView)
        progressBar = findViewById(R.id.progressBar)
        citiesRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            CityAdapter(arrayListOf()) { city ->
                val intent = Intent(this, WeatherDetailActivity::class.java)
                intent.putExtra(CITY_LAT, city.lat)
                intent.putExtra(CITY_LOG, city.lon)
                startActivity(intent)
            }


        citiesRecyclerView.adapter = adapter


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                Toast.makeText(this@HomeActivity, "Search: $query", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length > 3) {
                    // Call API to search for cities
                    searchViewModel.fetchCity(newText)

                }
                return true
            }
        })
    }


    private fun setupViewModel() {
        searchViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService)
            )
        )[SearchViewModel::class.java]
    }


    private fun setupObserver() {
        searchViewModel.getUiState().observe(this) {
            when (it) {
                is UiState.Success -> {
                    progressBar.visibility = View.GONE
                    renderList(it.data)
                    citiesRecyclerView.visibility = View.VISIBLE
                }

                is UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    citiesRecyclerView.visibility = View.GONE
                }

                is UiState.Error -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderList(users: List<City>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}

