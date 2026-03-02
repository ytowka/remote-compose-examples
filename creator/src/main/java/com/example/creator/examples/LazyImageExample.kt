package com.example.creator.examples

import android.annotation.SuppressLint
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteImage
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberNamedRemoteBitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun LazyImageExample() {
    val bitmap = rememberNamedRemoteBitmap(
        name = "image",
        url = "https://raw.githubusercontent.com/test-images/png/refs/heads/main/202105/cs-black-000.png",
    )

    RemoteImage(
        modifier = RemoteModifier.size(100.rdp),
        remoteBitmap = bitmap,
        contentDescription = null
    )

}