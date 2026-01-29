package com.example.creator

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.remote.creation.compose.action.HostAction
import androidx.compose.remote.creation.compose.action.ValueChange
import androidx.compose.remote.creation.compose.capture.captureSingleRemoteDocument
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteRow
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.state.MutableRemoteInt
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberMutableRemoteFloat
import androidx.compose.remote.creation.compose.state.rememberRemoteInt
import androidx.compose.remote.creation.compose.state.rememberRemoteString
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.ri
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

object ExampleScreen {

    @SuppressLint("RestrictedApi")
    suspend fun createDocument(context: Context) : ByteArray {
        return captureSingleRemoteDocument(
            context = context,
            content = {
                //RemoteText(text = "Hello Remote Compose")
                RemoteScreen()
            }
        ).bytes
    }


    @SuppressLint("RestrictedApi")
    @Composable
    @RemoteComposable
    fun RemoteScreen() {
        val intState = remember { MutableRemoteInt(0) }

        val stringState = rememberRemoteString { "start" }

        RemoteColumn(
            modifier = RemoteModifier
                .background(color = Color.Gray)
                .fillMaxSize(),
        ) {
            RemoteText(text = "Hello Remote Compose")
            RemoteBox(modifier = RemoteModifier.size(12.rdp))
            repeat(3) {
                RemoteRow(
                    modifier = RemoteModifier
                        .size(50.rdp)
                        .background(color = Color.White)
                        //.clickable(HostAction("value".rs,it.ri))
                        .clickable(ValueChange(stringState, "end"))
                    ,
                    verticalAlignment = RemoteAlignment.CenterVertically,
                    horizontalArrangement = RemoteArrangement.CenterHorizontally
                ) {
                    RemoteText(text = "$it")
                }
                RemoteBox(modifier = RemoteModifier.size(12.rdp))
                RemoteText(text = stringState)
            }
        }
    }
}

@Composable
@Preview
@RemoteComposable
private fun ScreenPreview() {
    ExampleScreen.RemoteScreen()
}