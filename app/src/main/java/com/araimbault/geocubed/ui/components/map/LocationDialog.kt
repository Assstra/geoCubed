package com.araimbault.geocubed.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.araimbault.geocubed.model.Location
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDialog(location: Location, onDismiss: () -> Unit, onSelect: (String) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        scrimColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.32f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(16.dp)
        ) {
            // Header: Location Name and Locality
            Text(
                text = location.properties.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = location.properties.locality,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Transportation Options Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    // Car Icon Button
                    IconButton(
                        onClick = {
                            onSelect("driving-car")
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                            onDismiss()
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .background(color = Color.LightGray, shape = CircleShape)
                            .padding(8.dp)
                    ) {
                        Icon(Icons.Filled.DirectionsCar, contentDescription = "Car", tint = Color.Black)
                    }

                    // Bike Icon Button
                    IconButton(
                        onClick = {
                            onSelect("cycling-regular")
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                            onDismiss()
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .background(color = Color.LightGray, shape = CircleShape)
                            .padding(8.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.DirectionsBike, contentDescription = "Bike", tint = Color.Black)
                    }

                    // Foot-walking Icon Button
                    IconButton(
                        onClick = {
                            onSelect("foot-walking")
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                            onDismiss()
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .background(color = Color.LightGray, shape = CircleShape)
                            .padding(8.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.DirectionsWalk, contentDescription = "Foot", tint = Color.Black)
                    }
                }
            )

            // Close Button
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                    onDismiss()
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
            ) {
                Icon(Icons.Filled.Close, contentDescription = "Close", tint = Color.Gray)
            }
        }
    }
}
