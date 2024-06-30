package com.araimbault.geocubed.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiFlags
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.araimbault.geocubed.R
import com.araimbault.geocubed.data.SearchLocationViewModel
import com.araimbault.geocubed.data.UserLanguageViewModel
import com.araimbault.geocubed.data.UserLocationViewModel
import com.araimbault.geocubed.model.DirectionResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.data.geojson.GeoJsonLayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun MapScreen(
    navController: NavController,
    userLocationViewModel: UserLocationViewModel,
    searchViewModel: SearchLocationViewModel,
    userLanguageViewModel: UserLanguageViewModel,
    onNextButtonClicked: () -> Unit = {}
) {
    val state = userLocationViewModel.currentLocation.collectAsState()
    val languageState by userLanguageViewModel.languageState.collectAsState()
    val userLocation = LatLng(state.value.lat, state.value.long)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 10f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = userLocation),
                title = stringResource(id = R.string.current_location),
                snippet = stringResource(id = R.string.current_location_message)
            )
        }

        SearchBar(onNextButtonClicked, searchViewModel)


        IconButton(
            onClick = { userLanguageViewModel.changeLanguage() },
            modifier = Modifier
                .size(70.dp)
                .background(color = Color.White, shape = CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            // Change language depending on value
            val flagResourceId = when (languageState.language) {
                "en" -> R.drawable.en
                "fr" -> R.drawable.fr
                else -> R.drawable.en
            }
            Image(
                painterResource(flagResourceId),
                "Flag",
                Modifier.size(50.dp)
            )
        }
    }
}