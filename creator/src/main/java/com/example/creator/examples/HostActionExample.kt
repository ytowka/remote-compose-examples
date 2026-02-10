package com.example.creator.examples

import android.annotation.SuppressLint
import androidx.compose.remote.creation.compose.action.HostAction
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.fillMaxWidth
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.modifier.rememberRemoteScrollState
import androidx.compose.remote.creation.compose.modifier.verticalScroll
import androidx.compose.remote.creation.compose.state.RemoteColor
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.ri
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.runtime.Composable
import androidx.core.graphics.toColorInt
import com.example.creator.RemoteButton
import com.example.creator.RemoteSpacer

@SuppressLint("RestrictedApi")
private val regularColor = RemoteColor("#CBCBCB".toColorInt())

@SuppressLint("RestrictedApi")
private val selectedColor = RemoteColor("#FF5252".toColorInt())



@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun HostActionExample(
    buttons: List<String>,
    selected: Int
) {
    RemoteColumn(
        modifier = RemoteModifier
            .verticalScroll(rememberRemoteScrollState())
            .padding(horizontal = 24.rf)
            .fillMaxSize(),
        verticalArrangement = RemoteArrangement.Center,
        horizontalAlignment = RemoteAlignment.CenterHorizontally,
    ) {
        buttons.forEachIndexed { i, text ->
            RemoteButton(
                modifier = RemoteModifier
                    .fillMaxWidth(),
                color = if(selected == i) selectedColor else regularColor,
                text = text.rs,
                actions = listOf(HostAction("buttonClick".rs, i.ri))
            )
            RemoteSpacer(12.rdp)
        }
    }
}