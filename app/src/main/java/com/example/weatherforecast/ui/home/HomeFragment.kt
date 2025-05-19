package com.example.weatherforecast.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.databinding.FragmentHomeBinding
import com.example.weatherforecast.domain.repository.UiState
import com.example.weatherforecast.utils.queryTextChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var adapter: CityAdapter

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setupUI()
        observeUiState()
        return binding.root
    }

    private fun setupUI() = with(binding) {
        adapter = CityAdapter { city ->
            val action = HomeFragmentDirections
                .actionCitySearchFragmentToWeatherDetailFragment(
                    city.lat!!.toFloat(),
                    city.lon!!.toFloat()
                )
            findNavController().navigate(action)
        }

        citiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        citiesRecyclerView.adapter = adapter

        lifecycleScope.launch {
            searchView.queryTextChanges()
                .debounce(300) // Wait for 300ms pause in typing
                .filter { it.length >= 3 } // Only process meaningful input
                .distinctUntilChanged() // Avoid duplicate queries
                .flowOn(Dispatchers.Default)
                .collect { query ->
                    searchViewModel.fetchCity(query)
                }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.citiesRecyclerView.visibility = View.GONE
                        }

                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(state.data)
                            binding.citiesRecyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG)
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

    private fun renderList(mCities: List<City>) {
        adapter.submitList(mCities)
    }
}

