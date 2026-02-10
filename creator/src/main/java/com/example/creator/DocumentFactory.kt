package com.example.creator

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.remote.creation.CreationDisplayInfo
import androidx.compose.remote.creation.compose.ExperimentalRemoteCreationComposeApi
import androidx.compose.remote.creation.compose.RemoteComposeCreationComposeFlags
import androidx.compose.remote.creation.compose.capture.WriterEvents
import androidx.compose.remote.creation.compose.capture.captureSingleRemoteDocument
import androidx.compose.remote.creation.compose.capture.createCreationDisplayInfo
import androidx.compose.remote.creation.compose.v2.captureRemoteDocumentV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
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
            val document = createDocumentV1(context, content)
            emit(document)
        }
    }
}

suspend fun createDocumentV1(
    context: Context,
    content: @Composable () -> Unit
): ByteArray {
    return captureSingleRemoteDocument(
        context = context,
        content = {
            content()
        },
        creationDisplayInfo = CreationDisplayInfo(
            context.resources.displayMetrics.widthPixels,
            context.resources.displayMetrics.heightPixels,
            context.resources.displayMetrics.densityDpi,
        )
    ).bytes
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
