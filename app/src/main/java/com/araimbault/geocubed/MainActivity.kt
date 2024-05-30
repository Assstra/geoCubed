package com.araimbault.geocubed

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.araimbault.geocubed.ui.components.navigation.NavController
import com.araimbault.geocubed.ui.theme.GeoCubedTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                Toast.makeText(this, "Precise location access granted.", Toast.LENGTH_SHORT).show()
                // TODO: Add logic to handle precise location access
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                Toast.makeText(this, "Approximate location access granted.", Toast.LENGTH_SHORT)
                    .show()
                // TODO: Add logic to handle approximate location access
            }

            else -> {
                // No location access granted.
                Toast.makeText(this, "No location access granted.", Toast.LENGTH_SHORT).show()
                // TODO: Add logic to handle no location access
            }
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {

        checkLocationPermissions()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            GeoCubedTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavController(
                        navController = navController,

                        fusedLocationClient,
                    )
                }
            }
        }
    }

    private fun checkLocationPermissions() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // Precise location access granted.
                Toast.makeText(this, "Precise location access already granted.", Toast.LENGTH_SHORT)
                    .show()
                // TODO: Add logic to handle already granted precise location access
            }

            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                // Only approximate location access granted.
                Toast.makeText(
                    this,
                    "Approximate location access already granted.",
                    Toast.LENGTH_SHORT
                ).show()
                // TODO: Add logic to handle already granted approximate location access
            }

            else -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }
}
