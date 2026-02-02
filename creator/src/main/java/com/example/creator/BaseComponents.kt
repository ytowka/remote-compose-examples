package com.example.creator

import android.annotation.SuppressLint
import androidx.compose.remote.creation.compose.action.Action
import androidx.compose.remote.creation.compose.capture.RemoteDensity
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteOffset
import androidx.compose.remote.creation.compose.layout.RemoteRow
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.drawWithContent
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.state.RemoteDp
import androidx.compose.remote.creation.compose.state.RemotePaint
import androidx.compose.remote.creation.compose.state.RemoteString
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun RemoteSpacer(size: RemoteDp) {
    RemoteBox(modifier = RemoteModifier.size(size))
}

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun RemoteButton(
    text: RemoteString,
    paddings: RemoteDp = 12.rdp,
    vararg actions: Action
) {
    RemoteRow(
        modifier = RemoteModifier
            .drawWithContent {
                val paint = android.graphics.Paint().also {
                    it.color = android.graphics.Color.WHITE
                }
                val size = RemoteDensity.HOST.density * 12f
                drawRoundRect(RemotePaint(paint), cornerRadius = RemoteOffset(size, size))
                drawContent()
            }
            .clickable(actions = actions),
        verticalAlignment = RemoteAlignment.CenterVertically,
        horizontalArrangement = RemoteArrangement.CenterHorizontally
    ) {

        RemoteSpacer(paddings)
        RemoteColumn(
            horizontalAlignment = RemoteAlignment.CenterHorizontally,
            verticalArrangement = RemoteArrangement.Center
        ) {

            RemoteSpacer(paddings)
            RemoteText(text = text, fontSize = 20.sp)
            RemoteSpacer(paddings)
        }
        RemoteSpacer(paddings)
    }
}