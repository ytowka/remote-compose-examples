package com.example.creator.examples

import android.annotation.SuppressLint
import androidx.compose.remote.creation.compose.action.HostAction
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteRow
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.fillMaxWidth
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.modifier.rememberRemoteScrollState
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.modifier.verticalScroll
import androidx.compose.remote.creation.compose.state.RemoteColor
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.creator.RemoteSpacer
import com.example.creator.background

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
fun LargeExample() {
    RemoteColumn(
        modifier = RemoteModifier
            .verticalScroll(rememberRemoteScrollState())
            .padding(horizontal = 24.rf)
            .fillMaxSize(),
        verticalArrangement = RemoteArrangement.Center,
        horizontalAlignment = RemoteAlignment.CenterHorizontally,
    ) {
        repeat(5) {
            RemoteRow(
                modifier = RemoteModifier
                    .fillMaxWidth()
                    .background(backgroundColor, 12.rdp)
                    .clickable(HostAction(name = "click".rs, value = "payload".rs))
                    .padding(horizontal = 30.rf, vertical = 24.rf)
            ) {
                RemoteBox(
                    modifier = RemoteModifier
                        .size(40.rdp)
                        .background(secondaryColor, 12.rdp)
                )
                RemoteSpacer(12.rdp)
                RemoteColumn {
                    RemoteText(text = "Title")
                    RemoteSpacer(12.rdp)
                    RemoteText(text = "Subtitle")
                }
            }
        }
    }
}