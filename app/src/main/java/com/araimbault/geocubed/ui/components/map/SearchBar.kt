package com.araimbault.geocubed.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.araimbault.geocubed.R
import com.araimbault.geocubed.data.SearchLocationViewModel
import com.araimbault.geocubed.model.Location

private const val COUNTRY = "FR"

@Composable
fun SearchBar(
    onNextButtonClicked: () -> Unit,
    searchViewModel: SearchLocationViewModel,
) {
    val locationsState by searchViewModel.searchState.collectAsState()
    var searchInput by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf<Location?>(null) }

    LaunchedEffect(searchInput) {
        // This block will be executed whenever `searchInput` changes
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
            placeholder = { Text("${stringResource(id = R.string.research_title)} ...")},
            singleLine = true,
        )
        locationsState.locationList?.features?.forEach { location ->
            if (location.properties.locality != null) {
                LocationItem(searchViewModel, location, onClick = {
                    selectedLocation = location
                })
            }
        }
    }

    selectedLocation?.let { location ->
        LocationDialog(
            location = location,
            onDismiss = { selectedLocation = null },
            onSelect = { profile: String ->
                searchViewModel.selectLocation(location, profile)
                onNextButtonClicked()
            }
        )
    }
}

