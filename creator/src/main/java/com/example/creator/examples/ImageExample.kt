package com.example.creator.examples

import android.annotation.SuppressLint
import androidx.compose.remote.creation.compose.layout.RemoteCanvas
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberRemoteBitmap
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.v2.RemoteImageV2
import androidx.compose.runtime.Composable

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun ImageExample() {
    RemoteColumn(
        modifier = RemoteModifier
            .padding(all = 16.rf)
            .fillMaxSize(),
    ) {
        val bitmap = rememberRemoteBitmap(
            name = "image",
            url = "https://raw.githubusercontent.com/test-images/png/refs/heads/main/202105/cs-black-000.png",
            width = 100,
            height = 100,
        )


        RemoteCanvas(
            modifier = RemoteModifier.size(100.rdp)
        ) {
            drawImage(bitmap)
        }
    }
}