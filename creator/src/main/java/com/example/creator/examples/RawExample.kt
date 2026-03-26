package com.example.creator.examples

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.remote.core.RcPlatformServices
import androidx.compose.remote.core.RemoteComposeBuffer
import androidx.compose.remote.core.operations.layout.managers.ColumnLayout
import androidx.compose.remote.creation.RemoteComposeContext
import androidx.compose.remote.creation.RemoteComposeWriter
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.state.RemoteColor
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.modifiers.RecordingModifier
import androidx.compose.remote.creation.profile.RcPlatformProfiles
import androidx.compose.runtime.Composable
import com.example.creator.background

@SuppressLint("RestrictedApi")
fun getRawBytesExample(): ByteArray {

    val writer = RemoteComposeWriter(
        1080, 2400, null, RcPlatformServices.None,
    ).apply {
        root {
            column(
                RecordingModifier()
                    .width(120)
                    .height(56)
                    .background(Color.BLACK),
                ColumnLayout.START,
                ColumnLayout.TOP
            ) {
                // other content
            }
        }
    }

    return writer.encodeToByteArray()
}

@SuppressLint("RestrictedApi")
@RemoteComposable
@Composable
fun getRawBytesAnalogExample() {
    RemoteColumn(
        modifier = RemoteModifier
            .size(120.rdp, 56.rdp)
            .background(RemoteColor(Color.BLACK))
    ) {

    }
}