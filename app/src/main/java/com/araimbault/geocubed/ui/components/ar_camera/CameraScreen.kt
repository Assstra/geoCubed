package com.araimbault.geocubed.ui.components.ar_camera

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CameraScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val currentContext = LocalContext.current
        Text(text = "Screen1")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            Toast.makeText(currentContext, "toto", Toast.LENGTH_LONG).show()
        }) {
            Text("Increase Score")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview(){
    CameraScreen(navController = rememberNavController())
}