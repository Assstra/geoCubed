package com.araimbault.geocubed.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.araimbault.geocubed.ui.theme.GeoCubedTheme
import com.araimbault.geocubed.data.SearchLocationViewModel
import com.araimbault.geocubed.model.Location
import com.araimbault.geocubed.network.searchLocations

private const val COUNTRY = "FR"

@Composable
fun SearchBar(searchViewModel: SearchLocationViewModel = viewModel()) {
    val locationsState by searchViewModel.searchState.collectAsState()
    var searchInput by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf<Location?>(null) }

    LaunchedEffect(searchInput) {
        // This block will be executed whenever `text` changes
        if (searchInput.isNotEmpty()) {
            val locations = searchViewModel.searchLocationsRepository(searchInput, COUNTRY)
            println("LOCATIONS : $locations")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0f))
            .padding(16.dp)
    ) {
        TextField(
            value = searchInput,
            onValueChange = { newValue ->
                searchInput = newValue
            }, //{ newValue: String -> searchViewModel.onSearchTextChanged(newValue) },

            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp)
                ),
            placeholder = { Text("Search...") },
            singleLine = true,
        )

        locationsState.locationList?.features?.forEach { location ->
            LocationItem(searchViewModel, location, onClick = {
                selectedLocation = location
            })
        }
    }

    selectedLocation?.let { location ->
        LocationDialog(
            location = location,
            onDismiss = { selectedLocation = null },
            onSelect = { searchViewModel.selectLocation(location) })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GeoCubedTheme {
        SearchBar()
    }
}
