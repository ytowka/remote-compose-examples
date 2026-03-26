package com.example.creator.examples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun EmptyExample() {
    val state = remember { mutableStateOf(false) }
    val state2 = remember { mutableStateOf(false) }

    if(state.value) {
        Box(modifier = Modifier.fillMaxSize())
    } else {
        Spacer(modifier = Modifier.fillMaxSize())
    }
}