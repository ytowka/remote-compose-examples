package com.example.creator.examples

import android.annotation.SuppressLint
import androidx.compose.remote.creation.compose.action.HostAction
import androidx.compose.remote.creation.compose.action.ValueChange
import androidx.compose.remote.creation.compose.capture.LocalRemoteComposeCreationState
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.graphicsLayer
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.modifier.rememberRemoteScrollState
import androidx.compose.remote.creation.compose.modifier.verticalScroll
import androidx.compose.remote.creation.compose.modifier.visibility
import androidx.compose.remote.creation.compose.state.RemoteColor
import androidx.compose.remote.creation.compose.state.animateRemoteFloat
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberMutableRemoteFloat
import androidx.compose.remote.creation.compose.state.rememberMutableRemoteInt
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.remote.creation.compose.state.rsp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.creator.RemoteButton
import com.example.creator.RemoteSpacer

@SuppressLint("RestrictedApi")
@RemoteComposable
@Composable
fun AnimationSimpleExample() {
    val visibility = rememberMutableRemoteInt(0)

    val opacity = animateRemoteFloat(
        rf = visibility.toRemoteFloat(),
        duration = 0.3f,
        initialValue = 0f
    )

    RemoteColumn(
        modifier = RemoteModifier
            .fillMaxSize(),
        horizontalAlignment = RemoteAlignment.CenterHorizontally,
    ) {
        RemoteText(
            modifier = RemoteModifier
                .visibility(visibility)
                .graphicsLayer(alpha = opacity),
            text = "animated",
            color = RemoteColor(Color.Black),
            fontSize = 20.rsp
        )
        RemoteSpacer(12.rdp)
        RemoteText(
            text = "animate: ".rs + opacity.toRemoteString(before = 1),
            modifier = RemoteModifier
                .background(color = Color.LightGray)
                .clickable(
                    ValueChange(visibility, (visibility + 1) % 2),
                    HostAction(name = "changed_visibility".rs, value = visibility)
                )
                .padding(8.rdp),
            fontSize = 20.rsp
        )
    }
}