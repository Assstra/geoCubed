package com.araimbault.geocubed.data

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UserLocationState(
    val lat: Double = 0.0,
    val long: Double = 0.0,
)

class UserLocationViewModel: ViewModel() {
    private val _currentLocation = MutableStateFlow(UserLocationState())
    val currentLocation: StateFlow<UserLocationState> = _currentLocation.asStateFlow()

    fun updateLocation(newLatLng: LatLng) {
        _currentLocation.update { currentState -> currentState.copy(
            lat = newLatLng.latitude,
            long = newLatLng.longitude,
        )}
    }
}
