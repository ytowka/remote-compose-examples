package com.example.creator.examples

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.remote.creation.compose.action.HostAction
import androidx.compose.remote.creation.compose.action.ValueChange
import androidx.compose.remote.creation.compose.capture.RemoteDensity
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.modifier.visibility
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberRemoteIntValue
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.creator.RemoteButton
import com.example.creator.RemoteSpacer

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun StateChangeExample() {
    val visibilityIntState = rememberRemoteIntValue { 0 }

    RemoteColumn(
        modifier = RemoteModifier
            .background(color = Color.Gray)
            .padding(all = 16.rf)
            .fillMaxSize(),
    ) {
        RemoteSpacer(12.rdp)
        RemoteText(text = "Hello Remote Compose")
        RemoteSpacer(12.rdp)
        repeat(3) {
            RemoteButton(
                text = "button $it".rs,
                paddings = 12.rdp,
                ValueChange(visibilityIntState, 1),
                HostAction("action".rs, "clicked $it".rs)
                //ValueChange(intState, selectIfGE(intState, 1.ri, 0.ri, 1.ri)),
                //ValueChange(intState, selectIfGT(intState, 0.ri, 0.ri, 1.ri)),

            )
            RemoteSpacer(12.rdp)
        }
        RemoteSpacer(12.rdp)
        RemoteText(text = visibilityIntState.toRemoteString(1))
        RemoteText(
            modifier = RemoteModifier.visibility(visibilityIntState),
            text = "updated",
        )
        RemoteSpacer(12.rdp)
        RemoteText(text = visibilityIntState.toRemoteString(1))
    }
}