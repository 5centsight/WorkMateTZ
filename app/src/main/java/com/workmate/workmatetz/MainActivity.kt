package com.workmate.workmatetz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.workmate.workmatetz.presentation.navigation.AppNavGraph
import com.workmate.workmatetz.presentation.theme.WorkMateTZTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkMateTZTheme {
                AppNavGraph()
            }
        }
    }
}