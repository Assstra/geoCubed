package com.araimbault.geocubed.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.araimbault.geocubed.data.ScoreViewModel

@Composable
fun NavController(navController: NavHostController, scoreState: ScoreViewModel) {
    NavHost(navController, startDestination = "destination_screen1") {
        composable("destination_screen1") {
            NavigationBottomBar(navController)
            Screen1(navController, scoreState)
        }
        composable("destination_screen2") {
            NavigationBottomBar(navController)
            Screen2(navController, scoreState)
        }
    }
}