package com.example.newcomposesample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.newcomposesample.ui.theme.NewComposeSampleTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi", "UnusedBoxWithConstraintsScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Column(
                modifier = Modifier.safeDrawingPadding()
            ) {
                NewComposeSampleTheme {
                    RemoteSimpleScreen()
                }
            }
        }
    }
}