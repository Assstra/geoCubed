package com.araimbault.geocubed.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.araimbault.geocubed.data.SearchLocationViewModel
import com.araimbault.geocubed.model.Location

@Composable
fun LocationItem(
    searchViewModel: SearchLocationViewModel,
    location: Location,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White.copy(alpha = 0.5f))
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = location.properties.name,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = location.properties.locality,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}