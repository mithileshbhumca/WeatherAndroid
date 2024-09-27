package com.example.weatherforecast.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.repository.UiState
import com.example.weatherforecast.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var adapter: CityAdapter

    private lateinit var citiesRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: SearchView

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setupUI()
        setupViewModel()
        setupObserver()

        return binding.root
    }

    private fun setupUI() {
        searchView = binding.searchView
        citiesRecyclerView = binding.citiesRecyclerView
        progressBar = binding.progressBar
        citiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter =
            CityAdapter(arrayListOf()) { city ->
                val action =
                    com.example.weatherforecast.ui.home.HomeFragmentDirections.actionCitySearchFragmentToWeatherDetailFragment(
                        city.lat!!.toFloat(),
                        city.lon!!.toFloat()
                    )
                findNavController().navigate(action)

            }
        citiesRecyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                //Toast.makeText(activity, "Search: $query", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    renderList(emptyList())
                } else if (newText.length > 3) {
                    searchViewModel.fetchCity(newText)
                }
                return true
            }
        })

    }
    private fun setupViewModel() {
       // val apiService = ApiHelperImpl(RetrofitBuilder.getApiService(requireContext()))
       // val factory = ViewModelFactory(apiService)
//        searchViewModel = ViewModelProvider(
//            this,
//            factory
//        )[SearchViewModel::class.java]
    }


    private fun setupObserver() {
        searchViewModel.getUiState().observe(viewLifecycleOwner) {
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
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderList(users: List<City>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}

