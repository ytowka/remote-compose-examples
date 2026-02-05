package com.example.creator.examples

import android.annotation.SuppressLint
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.runtime.Composable

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun SimpleExample() {
    RemoteText(
        text = "Hello world",
    )
}