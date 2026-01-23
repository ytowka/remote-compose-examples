package com.example.creator

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.remote.creation.compose.action.Action
import androidx.compose.remote.creation.compose.action.HostAction
import androidx.compose.remote.creation.compose.capture.captureSingleRemoteDocument
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemotePaddingValues
import androidx.compose.remote.creation.compose.layout.RemoteRow
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.state.RemoteDp
import androidx.compose.remote.creation.compose.state.RemoteFloat
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.ri
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.remote.creation.compose.v2.RemoteColumnV2
import androidx.compose.remote.creation.compose.v2.RemoteSpacerV2
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
                        .clickable(HostAction("value".rs,it.ri))
                    ,
                    verticalAlignment = RemoteAlignment.CenterVertically,
                    horizontalArrangement = RemoteArrangement.CenterHorizontally
                ) {
                    RemoteText(text = "$it")
                }
                RemoteBox(modifier = RemoteModifier.size(12.rdp))
            }
        }
    }
}

@Composable
@Preview
private fun ScreenPreview() {
    ExampleScreen.RemoteScreen()
}