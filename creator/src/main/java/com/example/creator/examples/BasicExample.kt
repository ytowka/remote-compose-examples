package com.example.creator.examples

import android.content.Context
import androidx.compose.remote.creation.compose.capture.captureSingleRemoteDocument
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.fillMaxWidth
import androidx.compose.remote.creation.compose.modifier.height
import androidx.compose.remote.creation.compose.state.RemoteColor
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rsp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

suspend fun getBasicDocument(context: Context): ByteArray {
    return captureSingleRemoteDocument(
        context = context,
        content = {
            BasicExample()
        },
    ).bytes
}

@Composable
@RemoteComposable
fun BasicExample() {
    RemoteColumn(
        modifier = RemoteModifier
            .background(color = RemoteColor(Color.Gray))
            .height(50.rdp)
            .fillMaxWidth(),
        verticalArrangement = RemoteArrangement.Center,
        horizontalAlignment = RemoteAlignment.CenterHorizontally
    ) {
        RemoteText(
            text = "Hello world!",
            fontSize = 24.rsp
        )
    }
}