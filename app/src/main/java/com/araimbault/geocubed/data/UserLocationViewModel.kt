package com.araimbault.geocubed.data

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.lang.reflect.Constructor

data class UserLocationState(
    val lat: Double = 0.0,
    val long: Double = 0.0,
)

class UserLocationViewModel(private val fusedLocationClient: FusedLocationProviderClient) :
    ViewModel() {

    private val _currentLocation = MutableStateFlow(UserLocationState())
    val currentLocation: StateFlow<UserLocationState> = _currentLocation.asStateFlow()

    private val locationRequest = LocationRequest.Builder(1000) // 1 second interval
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .build()

    private val locationCallback =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    updateLocation(
                        LatLng(
                            it.latitude,
                            it.longitude
                        )
                    )
                }
            }
        }
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun updateLocation(newLatLng: LatLng) {
        _currentLocation.update { currentState ->
            currentState.copy(
                lat = newLatLng.latitude,
                long = newLatLng.longitude,
            )
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onCleared() {
        stopLocationUpdates()
    }

    init {
        startLocationUpdates()
    }
}
