package com.example.creator

import android.annotation.SuppressLint
import androidx.compose.remote.creation.compose.ExperimentalRemoteCreationComposeApi
import androidx.compose.remote.creation.compose.RemoteComposeCreationComposeFlags
import androidx.compose.remote.creation.compose.capture.WriterEvents
import androidx.compose.remote.creation.compose.capture.captureSingleRemoteDocument
import androidx.compose.remote.creation.compose.capture.createCreationDisplayInfo
import androidx.compose.remote.creation.compose.v2.captureRemoteDocumentV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take

@OptIn(ExperimentalRemoteCreationComposeApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun createDocumentV1(content: @Composable () -> Unit): Flow<ByteArray?> {
    SideEffect {
        RemoteComposeCreationComposeFlags.isRemoteApplierEnabled = false
    }
    val context = LocalContext.current
    return remember {
        flow {
            val document = captureSingleRemoteDocument(
                context = context,
                content = content
            ).bytes
            emit(document)
        }
    }
}

@OptIn(ExperimentalRemoteCreationComposeApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun createDocumentV2(content: @Composable () -> Unit): Flow<ByteArray?> {
    SideEffect {
        RemoteComposeCreationComposeFlags.isRemoteApplierEnabled = true
    }
    val context = LocalContext.current
    return remember {
        captureRemoteDocumentV2(
            creationDisplayInfo = createCreationDisplayInfo(context),
            writerEvents = WriterEvents(),
            content = content
        ).take(10)
    }
}
