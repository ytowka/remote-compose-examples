package com.example.creator.examples

import android.annotation.SuppressLint
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.creator.background

@SuppressLint("RestrictedApi")
fun getRawBytesExample(): ByteArray {

    val writer = RemoteComposeWriter(
        1080, 2400, null, RcPlatformServices.None,
    ).apply {
        val backgroundLight = addColor(Color(0xFFBDBDBD).toArgb())
        val backgroundDark = addColor(Color(0xFF2F2F2F).toArgb())

        val backgroundColor = addThemedColor(backgroundLight.toShort(), backgroundDark.toShort())


        root {
            column(
                RecordingModifier()
                    .backgroundId(backgroundColor)
                    //.background(Color.Black.toArgb())
                    .width(240)
                    .height(200),
                ColumnLayout.START,
                ColumnLayout.TOP
            ) {
                // other content
            }
        }
    }

    return writer.encodeToByteArray()
}
