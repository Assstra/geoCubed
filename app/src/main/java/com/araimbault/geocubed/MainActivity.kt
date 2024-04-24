package com.araimbault.geocubed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.araimbault.geocubed.data.ScoreViewModel
import com.araimbault.geocubed.ui.components.NavController
import com.araimbault.geocubed.ui.theme.GeoCubedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val scoreState = ScoreViewModel()
            GeoCubedTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavController(
                        navController = navController,
                        scoreState,
                    )
                }
            }
        }
    }
}
