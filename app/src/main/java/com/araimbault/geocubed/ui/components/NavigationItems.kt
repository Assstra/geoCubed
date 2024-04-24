package com.araimbault.geocubed.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route : String) {
    data object Screen1 : Screens("destination_screen1")
    data object Screen2 : Screens("destination_screen2")
    data object Map : Screens("destination_screen3")
}

//initializing the data class with default parameters
data class BottomNavigationItem(
    val label : String = "Home",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = "destination_screen1"
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Screens.Screen1.route
            ),
            BottomNavigationItem(
                label = "Search",
                icon = Icons.Filled.Search,
                route = Screens.Screen2.route
            ),
            BottomNavigationItem(
                label = "Profile",
                icon = Icons.Filled.LocationOn,
                route = Screens.Map.route
            ),
        )
    }
}