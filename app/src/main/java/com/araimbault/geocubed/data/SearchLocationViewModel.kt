package com.araimbault.geocubed.data

import androidx.lifecycle.ViewModel
import com.araimbault.geocubed.model.DirectionResponse
import com.araimbault.geocubed.model.Location
import com.araimbault.geocubed.model.LocationResponse
import com.araimbault.geocubed.network.searchDirections
import com.araimbault.geocubed.network.searchLocations
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SearchLocationState(
    var searchInput: String = "",
    var locationList: LocationResponse? = null,
    var destination: Location? = null,
    var profile: String = "driving-car",
    var directions: DirectionResponse? = null
)

class SearchLocationViewModel : ViewModel() {

    private val _searchState = MutableStateFlow(SearchLocationState())
    val searchState: StateFlow<SearchLocationState> = _searchState.asStateFlow()

    suspend fun searchLocationsRepository(searchInput: String, country: String): LocationResponse? {
        _searchState.update { currentState -> currentState.copy(searchInput = searchInput) }
        return try {
            val locations = searchLocations(searchInput, country)
            _searchState.update { currentState -> currentState.copy(locationList = locations) }
            _searchState.value.locationList
        } catch (e: Exception) {
            println("A problem occurred : $e")
            null
        }
    }

    fun selectLocation(location: Location, profile: String) {
        _searchState.update { currentState -> currentState.copy(destination = location) }
        _searchState.update { currentState -> currentState.copy(profile = profile) }
    }

    fun getDestination(): Location? {
        val destination = _searchState.value.destination
        println("Getting destination: $destination")
        return destination
    }

    suspend fun searchDirectionsRepository(startPos: LatLng, language: String): DirectionResponse? {
        val endPos = LatLng(
            _searchState.value.destination?.geometry?.coordinates?.get(1) ?: 0.0,
            _searchState.value.destination?.geometry?.coordinates?.get(0) ?: 0.0,
        )
        val profile = _searchState.value.profile

        return try {
            val directions = searchDirections(startPos, endPos, profile, language)
            _searchState.update { currentState -> currentState.copy(directions = directions) }
            _searchState.value.directions
        } catch (e: Exception) {
            println("A problem occurred : $e")
            null
        }
    }

    fun clearViewModel(){
        _searchState.value.searchInput = ""
        _searchState.value.locationList = null
        _searchState.value.destination = null
        _searchState.value.profile = "driving-car"
        _searchState.value.directions = null
    }
}
