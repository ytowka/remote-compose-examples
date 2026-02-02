package com.example.creator

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.remote.creation.compose.ExperimentalRemoteCreationComposeApi
import androidx.compose.remote.creation.compose.RemoteComposeCreationComposeFlags
import androidx.compose.remote.creation.compose.capture.WriterEvents
import androidx.compose.remote.creation.compose.capture.captureSingleRemoteDocument
import androidx.compose.remote.creation.compose.capture.createCreationDisplayInfo
import androidx.compose.remote.creation.compose.v2.captureRemoteDocumentV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take

@OptIn(ExperimentalRemoteCreationComposeApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun createDocumentV1(content: @Composable () -> Unit): State<ByteArray?> {
    SideEffect {
        RemoteComposeCreationComposeFlags.isRemoteApplierEnabled = false
    }
    val context = LocalContext.current
    val byteArray = remember { mutableStateOf<ByteArray?>(null) }
    LaunchedEffect(Unit) {
        byteArray.value = captureSingleRemoteDocument(
            context = context,
            content = content
        ).bytes
    }
    return byteArray
}

@OptIn(ExperimentalRemoteCreationComposeApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun createDocumentV2(content: @Composable () -> Unit): State<ByteArray?> {
    SideEffect {
        RemoteComposeCreationComposeFlags.isRemoteApplierEnabled = true
    }
    val context = LocalContext.current
    val byteArray = remember { mutableStateOf<ByteArray?>(null) }
    LaunchedEffect(Unit) {
        flow {
            emitAll(
                captureRemoteDocumentV2(
                    creationDisplayInfo = createCreationDisplayInfo(context),
                    writerEvents = WriterEvents(),
                    content = content
                ).take(1))
        }.collect {
            byteArray.value = it
            Log.d("debugg", "onUpdate() called ${it.size}")
        }
    }
    return byteArray
}
