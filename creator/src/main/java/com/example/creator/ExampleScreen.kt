package com.example.creator

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.remote.creation.compose.ExperimentalRemoteCreationComposeApi
import androidx.compose.remote.creation.compose.RemoteComposeCreationComposeFlags
import androidx.compose.remote.creation.compose.action.Action
import androidx.compose.remote.creation.compose.action.ValueChange
import androidx.compose.remote.creation.compose.capture.RemoteDensity
import androidx.compose.remote.creation.compose.capture.WriterEvents
import androidx.compose.remote.creation.compose.capture.captureSingleRemoteDocument
import androidx.compose.remote.creation.compose.capture.createCreationDisplayInfo
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteOffset
import androidx.compose.remote.creation.compose.layout.RemoteRow
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.drawWithContent
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.modifier.visibility
import androidx.compose.remote.creation.compose.state.RemoteDp
import androidx.compose.remote.creation.compose.state.RemotePaint
import androidx.compose.remote.creation.compose.state.RemoteString
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberRemoteIntValue
import androidx.compose.remote.creation.compose.state.rememberRemoteString
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.ri
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.remote.creation.compose.state.selectIfGT
import androidx.compose.remote.creation.compose.v2.captureRemoteDocumentV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow

object ExampleScreen {

    @OptIn(ExperimentalRemoteCreationComposeApi::class)
    @SuppressLint("RestrictedApi")
    suspend fun createDocument(context: Context) : ByteArray {
        RemoteComposeCreationComposeFlags.isRemoteApplierEnabled = false
        return captureSingleRemoteDocument(
            context = context,
            content = {
                RemoteScreen()
            }
        ).bytes
    }

    @OptIn(ExperimentalRemoteCreationComposeApi::class)
    @SuppressLint("RestrictedApi")
    fun createDocumentV2(/*optional*/context: Context) : Flow<ByteArray> {
        RemoteComposeCreationComposeFlags.isRemoteApplierEnabled = true
        return captureRemoteDocumentV2(
            creationDisplayInfo = createCreationDisplayInfo(context),
            writerEvents = WriterEvents(),
            content = {
                RemoteScreen()
            }
        )
    }


    @SuppressLint("RestrictedApi")
    @Composable
    @RemoteComposable
    fun RemoteScreen() {

        val stringState = rememberRemoteString { "start" }
        //val listState = rememberRemoteStringList("first", "second")
        val intState = rememberRemoteIntValue { 0 }

        val visibilityIntState = rememberRemoteIntValue { 0 }

        Log.d("debuggg", "RemoteScreen() called density = ${RemoteDensity.HOST}")
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
                        //ValueChange(intState, selectIfGE(intState, 1.ri, 0.ri, 1.ri)),
                    ValueChange(intState, selectIfGT(intState, 0.ri, 0.ri, 1.ri)),

                )
                RemoteSpacer(12.rdp)
            }
            RemoteText(
                modifier = RemoteModifier.visibility(intState),
                text = "updated"
            )
            RemoteText(text = intState.toRemoteString(1))
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    @RemoteComposable
    fun RemoteButton(
        text: RemoteString,
        paddings: RemoteDp = 12.rdp,
        vararg actions: Action
    ) {
        RemoteRow(
            modifier = RemoteModifier
                .drawWithContent {
                    val paint = android.graphics.Paint().also {
                        it.color = android.graphics.Color.WHITE
                    }
                    val size = RemoteDensity.HOST.density * 12f
                    drawRoundRect(RemotePaint(paint), cornerRadius = RemoteOffset(size, size))
                    drawContent()
                }
                .clickable(actions = actions)
            ,
            verticalAlignment = RemoteAlignment.CenterVertically,
            horizontalArrangement = RemoteArrangement.CenterHorizontally
        ) {
            
            RemoteSpacer(paddings)
            RemoteColumn(
                horizontalAlignment = RemoteAlignment.CenterHorizontally,
                verticalArrangement = RemoteArrangement.Center
            ) {

                RemoteSpacer(paddings)
                RemoteText(text = text, fontSize = 20.sp)
                RemoteSpacer(paddings)
            }
            RemoteSpacer(paddings)
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    @RemoteComposable
    fun RemoteSpacer(size: RemoteDp) {
        RemoteBox(modifier = RemoteModifier.size(size))
    }
}


@SuppressLint("RestrictedApi")
@Composable
fun createDocumentV1(): State<ByteArray?> {
    val context = LocalContext.current
    val byteArray = remember { mutableStateOf<ByteArray?>(null) }
    LaunchedEffect(Unit) {
        byteArray.value = ExampleScreen.createDocument(context).also {
            Log.d("debugg", "onCreate() called ${it.size}")
        }
    }
    return byteArray
}

@SuppressLint("RestrictedApi")
@Composable
fun createDocumentV2(): State<ByteArray?> {
    val context = LocalContext.current
    val byteArray = remember { mutableStateOf<ByteArray?>(null) }
    LaunchedEffect(Unit) {

        ExampleScreen.createDocumentV2(/*optional*/context).collect {
            byteArray.value = it
            Log.d("debugg", "onUpdate() called ${it.size}")
        }
    }
    return byteArray
}

@Composable
@Preview
@RemoteComposable
private fun ScreenPreview() {
    ExampleScreen.RemoteScreen()
}