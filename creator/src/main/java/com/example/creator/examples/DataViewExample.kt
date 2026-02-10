package com.example.creator.examples

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.remote.creation.compose.action.HostAction
import androidx.compose.remote.creation.compose.capture.RemoteDensity
import androidx.compose.remote.creation.compose.capture.RemoteImageVector
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteCanvas
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteOffset
import androidx.compose.remote.creation.compose.layout.RemoteRow
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.shaders.RemoteBrush
import androidx.compose.remote.creation.compose.shaders.RemoteLinearGradient
import androidx.compose.remote.creation.compose.state.RemoteColor
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberRemoteBitmap
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.creator.RemoteFancyText
import com.example.creator.RemoteGradientText
import com.example.creator.RemoteSpacer
import com.example.creator.background
import com.example.creator.gradientBackground

@SuppressLint("RestrictedApi")
private val backgroundColor = RemoteColor("#F2F3F5".toColorInt())

@SuppressLint("RestrictedApi")
private val secondaryColor = RemoteColor("#d0d0d0".toColorInt())

@SuppressLint("RestrictedApi")
private val blue = RemoteColor(Color.Blue)

@SuppressLint("RestrictedApi")
private val red = RemoteColor(Color.Red)

@Composable
@RemoteComposable
@SuppressLint("RestrictedApi")
fun DataViewExample() {
    RemoteRow(
        modifier = RemoteModifier
            .background(backgroundColor, 12.rdp)
            .clickable(HostAction(name = "click".rs, value = "payload".rs))
            .padding(horizontal = 30.rf, vertical = 24.rf)
    ) {
        RemoteBox(
            modifier = RemoteModifier
                .size(40.rdp)
                .gradientBackground(
                    colors = listOf(red, blue),
                    cornerRadius = 12.rdp
                )
                //.background(color = secondaryColor, 12.rdp),
        )
        RemoteSpacer(12.rdp)
        RemoteColumn {
            RemoteText(text = "Remote Title")
            RemoteSpacer(12.rdp)
            RemoteText(text = "Remote Subtitle")
        }
        RemoteSpacer(12.rdp)
        RemoteGradientText(
            modifier = RemoteModifier
                .size(40.rdp),
            text = "Fa".rs
        )
    }
}

@Composable
@RemoteComposable
@SuppressLint("RestrictedApi")
fun RemoteImage(modifier: RemoteModifier = RemoteModifier, url: String) {
    val bitmap = rememberRemoteBitmap(name = "remoteImage", url = url, width = 100, height = 100)

    RemoteCanvas(modifier = modifier.size(40.rdp)) {
        drawImage(bitmap)
    }
}