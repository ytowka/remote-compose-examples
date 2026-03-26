package com.example.creator.examples

import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.rememberRemoteScrollState
import androidx.compose.remote.creation.compose.modifier.verticalScroll
import androidx.compose.remote.creation.compose.state.rememberMutableRemoteInt
import androidx.compose.runtime.Composable

@Composable
@RemoteComposable
fun StateChangeExample() {
    val visibility = rememberMutableRemoteInt(0)

    RemoteColumn(
        modifier = RemoteModifier
            .clickable()
            .fillMaxSize()
            .verticalScroll(rememberRemoteScrollState())
            .fillMaxSize(),
        verticalArrangement = RemoteArrangement.Center,
        horizontalAlignment = RemoteAlignment.CenterHorizontally,
    ) {

    }
}