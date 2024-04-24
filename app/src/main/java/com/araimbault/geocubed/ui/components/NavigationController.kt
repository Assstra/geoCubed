package com.araimbault.geocubed.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.araimbault.geocubed.data.ScoreViewModel

@Composable
fun NavController(navController: NavHostController, scoreState: ScoreViewModel) {
    NavHost(navController, startDestination = Screens.Screen1.route) {
        composable(Screens.Screen1.route) {
            NavigationBottomBar(navController)
            Screen1(navController, scoreState)
        }
        composable(Screens.Screen2.route) {
            NavigationBottomBar(navController)
            Screen2(navController, scoreState)
        }
        composable(Screens.Map.route) {
            NavigationBottomBar(navController)
            Screen3(navController)
        }
    }
}