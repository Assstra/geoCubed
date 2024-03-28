package com.araimbault.geocubed.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.araimbault.geocubed.data.ScoreViewModel

@Composable
fun Screen1(navController: NavController, scoreState: ScoreViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Screen1")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scoreState.increaseScore()
        }) {
            Text("Increase Score")
        }
        Text(text = scoreState.score.value.toString())
    }
}