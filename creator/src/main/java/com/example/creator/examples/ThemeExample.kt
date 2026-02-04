package com.example.creator.examples

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.remote.core.operations.ColorAttribute
import androidx.compose.remote.core.operations.ColorTheme
import androidx.compose.remote.creation.compose.capture.LocalRemoteComposeCreationState
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.layout.WriteToDocument
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.drawWithContent
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.shaders.RemoteBrush
import androidx.compose.remote.creation.compose.shaders.solidColor
import androidx.compose.remote.creation.compose.state.RemoteColor
import androidx.compose.remote.creation.compose.state.RemotePaint
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.toInt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@SuppressLint("RestrictedApi")
@RemoteComposable
@Composable
fun ThemeExample() {
    val doc = LocalRemoteComposeCreationState.current.document

    val backgroundLight = doc.addColor(Color(0xFFBDBDBD).value.toInt())
    val backgroundDark = doc.addColor(Color(0xFF2F2F2F).value.toInt())

    val backgroundColor = doc.addThemedColor(backgroundLight.toShort(), backgroundDark.toShort())

    RemoteColumn(
        modifier = RemoteModifier
            .drawWithContent {
                val remoteBackgroundColor = RemoteColor(
                    doc.getColorAttribute(backgroundColor.toInt(), ColorAttribute.COLOR_ALPHA).rf,
                    doc.getColorAttribute(backgroundColor.toInt(), ColorAttribute.COLOR_RED).rf,
                    doc.getColorAttribute(backgroundColor.toInt(), ColorAttribute.COLOR_GREEN).rf,
                    doc.getColorAttribute(backgroundColor.toInt(), ColorAttribute.COLOR_BLUE).rf
                )
                val paint = RemotePaint().also {
                    it.remoteColor = RemoteColor(Color(0xFFBDBDBD))
                }
                drawRect(paint)
                drawContent()
            }
            .padding(all = 16.rf)
            .fillMaxSize(),
    ) {
        RemoteText(
            text = "Hello Remote Compose",
            fontSize = 18.sp,
            color = RemoteColor(Color(0xFF000000))
        )
    }
}