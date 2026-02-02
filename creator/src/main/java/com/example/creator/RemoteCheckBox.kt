package com.example.creator

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.remote.core.semantics.AccessibleComponent
import androidx.compose.remote.creation.actions.HostAction
import androidx.compose.remote.creation.compose.action.Action
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteCanvas
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.state.RemoteColor
import androidx.compose.remote.creation.compose.state.RemoteInt
import androidx.compose.remote.creation.compose.state.RemoteString
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.runtime.Composable

@SuppressLint("RestrictedApi")
@RemoteComposable
@Composable
fun RemoteCheckbox(
    checked: Boolean,                 // Текущее состояние
    onCheckedChange: (Boolean) -> Unit, // Callback
    modifier: RemoteModifier = RemoteModifier
) {
    val boxSize = 24.rdp
    val checkColor = if (checked) RemoteColor(Color.GREEN) else RemoteColor(Color.TRANSPARENT)
    val borderColor = RemoteColor(Color.GRAY)

    RemoteBox(
        modifier = modifier
            .clickable(
                actions = listOf<Action>(),
                enabled = true,
            ),
    ) {
        RemoteCanvas(
            modifier = RemoteModifier.size(boxSize, boxSize),
            content = {}
        )

    }
}