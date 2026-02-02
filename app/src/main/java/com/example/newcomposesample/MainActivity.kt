package com.example.newcomposesample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.remote.player.compose.ExperimentalRemotePlayerApi
import androidx.compose.remote.player.compose.RemoteComposePlayerFlags
import com.example.newcomposesample.ui.theme.NewComposeSampleTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalRemotePlayerApi::class)
    @SuppressLint("RestrictedApi", "UnusedBoxWithConstraintsScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        RemoteComposePlayerFlags.isViewPlayerEnabled = false

        setContent {
            NewComposeSampleTheme {
                RemoteScreen()
            }
        }
    }
}