package com.example.creator.examples

import android.annotation.SuppressLint
import androidx.compose.remote.creation.compose.capture.RemoteImageVector
import androidx.compose.remote.creation.compose.layout.RemoteCanvas
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.state.RemoteBitmap
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.v2.RemoteImageV2
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun ImageExample() {
    RemoteColumn(
        modifier = RemoteModifier
            .background(color = Color.Gray)
            .padding(all = 16.rf)
            .fillMaxSize(),
    ) {

    }
}