package com.araimbault.geocubed.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.araimbault.geocubed.ui.components.ar_camera.CameraScreen
import com.araimbault.geocubed.ui.components.map.MapScreen

import com.google.android.gms.location.FusedLocationProviderClient

@Composable
fun NavController(
    navController: NavHostController,

    fusedLocationProviderClient: FusedLocationProviderClient
) {
    NavHost(navController, startDestination = Screens.Camera.route) {
        composable(Screens.Camera.route) {
            NavigationBottomBar(navController)
            CameraScreen(navController)
        }
        composable(Screens.Map.route) {
            NavigationBottomBar(navController)
            MapScreen(navController, fusedLocationProviderClient)
        }
    }
}