package com.araimbault.geocubed.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.araimbault.geocubed.data.SearchLocationViewModel
import com.araimbault.geocubed.data.UserLanguageViewModel
import com.araimbault.geocubed.data.UserLocationViewModel
import com.araimbault.geocubed.ui.components.ar_camera.CameraScreen
import com.araimbault.geocubed.ui.components.map.DirectionScreen
import com.araimbault.geocubed.ui.components.map.MapScreen

//import com.araimbault.geocubed.utils.ARCoreSessionLifecycleHelper

@Composable
fun NavController(
    navController: NavHostController,
    userLocationViewModel: UserLocationViewModel,
    searchLocationViewModel: SearchLocationViewModel,
    userLanguageViewModel: UserLanguageViewModel,
) {
    NavHost(navController, startDestination = Screens.Map.route) {
        composable(Screens.Map.route) {
            NavigationBottomBar(navController)
            MapScreen(
                navController,
                userLocationViewModel,
                searchLocationViewModel,
                userLanguageViewModel,
                onNextButtonClicked = {
                    navController.navigate(Screens.Direction.route)
                },
            )
        }
        composable(Screens.Direction.route) {
            DirectionScreen(
                navController,
                userLocationViewModel,
                searchLocationViewModel,
                userLanguageViewModel,
                onCancelButtonClicked = {
                    navController.navigate(Screens.Map.route)
                    searchLocationViewModel.clearViewModel()
                },
            )
        }
        composable(Screens.Camera.route) {
            NavigationBottomBar(navController)
            CameraScreen(navController)
        }
    }
}