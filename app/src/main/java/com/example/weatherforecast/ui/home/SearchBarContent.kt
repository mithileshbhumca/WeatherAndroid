package com.example.weatherforecast.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.data.model.City

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarContent(
    query: String,
    expanded: Boolean,
    onQueryChange: (String)->Unit,
    onExpandedChange: (Boolean)->Unit,
    onSearch: () -> Unit,
    searchResults: List<City>,
    onResultClick: (City) -> Unit,
) {
    // Controls expansion state of the search bar
    var expanded by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier.fillMaxWidth()
            .semantics { traversalIndex = 0f },
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = {
                    onSearch()
                },
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = { Text("Search city") },
                leadingIcon = {Icon(
                    imageVector =
                        Icons.Default.Search,
                    contentDescription = "Search"
                )},
            )
        },
        expanded = expanded,
        onExpandedChange =onExpandedChange,
    ) {
        // Display search results in a scrollable column
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            items(searchResults){ city->
                ListItem(
                    headlineContent = {
                        Text(text = city.name?:"")
                    },
                    supportingContent = {
                        Text(
                            text = "${city.state ?: ""}, " +
                                    (city.country ?: "")
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{
                            onResultClick(city)
                        }
                )
            }

        }
    }
    //}
}
