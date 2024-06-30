package com.araimbault.geocubed.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.araimbault.geocubed.R
import com.araimbault.geocubed.data.SearchLocationViewModel
import com.araimbault.geocubed.data.UserLanguageViewModel
import com.araimbault.geocubed.data.UserLocationViewModel
import com.araimbault.geocubed.model.DirectionResponse
import com.araimbault.geocubed.utils.bitmapDescriptorFromVector
import com.araimbault.geocubed.utils.formatDistance
import com.araimbault.geocubed.utils.formatDuration
import com.araimbault.geocubed.utils.gsonToJSONObject
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.BuildConfig
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.data.geojson.GeoJsonLayer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectionScreen(
    navController: NavController,
    userLocationViewModel: UserLocationViewModel,
    searchLocationViewModel: SearchLocationViewModel,
    userLanguageViewModel: UserLanguageViewModel,
    onCancelButtonClicked: () -> Unit = {}
) {

    val context = LocalContext.current

    val userLocationState by userLocationViewModel.currentLocation.collectAsState()
    val searchState by searchLocationViewModel.searchState.collectAsState()
    val languageState by userLanguageViewModel.languageState.collectAsState()
    val userLocation = LatLng(userLocationState.lat, userLocationState.long)

    val destination = searchLocationViewModel.getDestination()
    var directionInfo by remember { mutableStateOf<DirectionResponse?>(null) }
    var destinationLocation by remember {
        mutableStateOf(
            LatLng(
                directionInfo?.bbox?.get(0) ?: 0.0,
                directionInfo?.bbox?.get(1) ?: 0.0
            )
        )
    }
    val formatedDuration = directionInfo?.features?.get(0)?.properties?.summary?.duration?.let {
        formatDuration(it)
    }
    val formatedDistance = directionInfo?.features?.get(0)?.properties?.summary?.distance?.let {
        formatDistance(it)
    }
    val steps = directionInfo?.features?.get(0)?.properties?.segments?.get(0)?.steps

    val scaffoldState = rememberBottomSheetScaffoldState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 15f)
    }

    val routeCoordinates = remember { mutableStateOf(listOf<LatLng>()) }

    val selectedLanguage = languageState.language

    LaunchedEffect(destination) {
        if (destination != null) {
            searchLocationViewModel.viewModelScope.launch {
                val result = searchLocationViewModel.searchDirectionsRepository(userLocation, selectedLanguage)
                directionInfo = result
                if (result != null) {
                    val coord = result.metadata.query.coordinates
                    destinationLocation = LatLng(coord[1][1], coord[1][0])

                    // Create bounds that include both the user location and the destination
                    val bounds = LatLngBounds.builder()
                        .include(userLocation)
                        .include(destinationLocation)
                        .build()

                    // Move the camera to fit the bounds with some padding
                    cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(bounds, 100))

                    println("DIRECTIONS : $result")

                    // Extract coordinates
                    val coordinates = result.features[0].geometry.coordinates
                    routeCoordinates.value = coordinates.map { LatLng(it[1], it[0]) }
                }
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 150.dp,
        sheetContainerColor = Color.White,
        sheetContent = {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.9f),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .offset(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = formatedDuration ?: "",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = formatedDistance ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            onCancelButtonClicked()
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(color = Color.White, shape = CircleShape)
                            .border(1.dp, Color.Gray, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Cancel",
                            tint = Color.Red
                        )
                    }
                }
            }
            steps?.let {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    items(steps.size) { index ->
                        StepView(step = steps[index])
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
            ) {
                Marker(
                    state = MarkerState(position = userLocation),
                    title = stringResource(id = R.string.current_location),
                    snippet = stringResource(id = R.string.current_location_message)
                )
                Polyline(
                    points = routeCoordinates.value,
                    color = Color.Blue,
                    width = 10f
                )

                val icon = bitmapDescriptorFromVector(
                    context, R.drawable.flag
                )

                destination?.let {
                    Marker(
                        state = MarkerState(position = destinationLocation),
                        title = stringResource(id = R.string.current_destination),
                        snippet = destination.properties.name,
                        icon = icon
                    )
                }
            }

            directionInfo?.let {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(16.dp)
                            .background(Color.White, RoundedCornerShape(12.dp)),
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                )
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            destination?.properties?.name?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                            destination?.properties?.let {
                                Text(
                                    text = it.locality + ", " + it.country,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}