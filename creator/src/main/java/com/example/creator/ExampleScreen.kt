package com.example.creator

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.remote.creation.compose.action.Action
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
import androidx.compose.remote.creation.compose.layout.rememberRemoteStringList
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.height
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.modifier.toComposeUi
import androidx.compose.remote.creation.compose.state.MutableRemoteInt
import androidx.compose.remote.creation.compose.state.RemoteDp
import androidx.compose.remote.creation.compose.state.RemoteString
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

        val stringState = rememberRemoteString { "start" }
        val listState = rememberRemoteStringList("first", "second")

        RemoteColumn(
            modifier = RemoteModifier
                .background(color = Color.Gray)
                .fillMaxSize(),
        ) {
            RemoteText(text = "Hello Remote Compose")
            RemoteSpacer(12.rdp)
            repeat(3) {
                RemoteButton(
                    text = it.ri.toRemoteString(10),
                    ValueChange(stringState, "end")
                )
                RemoteSpacer(12.rdp)
            }
            RemoteText(text = stringState)
            /*if(stringState == "end".rs) {
                RemoteText(text = "updated")
            }*/
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    @RemoteComposable
    fun RemoteButton(
        text: RemoteString,
        vararg actions: Action
    ) {
        RemoteRow(
            modifier = RemoteModifier
                .background(color = Color.White)
                .clickable(actions = actions)
            ,
            verticalAlignment = RemoteAlignment.CenterVertically,
            horizontalArrangement = RemoteArrangement.CenterHorizontally
        ) {
            
            RemoteSpacer(12.rdp)
            RemoteColumn(
                horizontalAlignment = RemoteAlignment.CenterHorizontally,
                verticalArrangement = RemoteArrangement.Center
            ) {

                RemoteSpacer(12.rdp)
                RemoteText(text = text)
                RemoteSpacer(12.rdp)
            }
            RemoteSpacer(12.rdp)
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    @RemoteComposable
    fun RemoteSpacer(size: RemoteDp) {
        RemoteBox(modifier = RemoteModifier.size(size))
    }
}

@Composable
@Preview
@RemoteComposable
private fun ScreenPreview() {
    ExampleScreen.RemoteScreen()
}