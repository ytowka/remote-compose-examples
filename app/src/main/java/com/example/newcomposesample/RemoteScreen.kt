package com.example.newcomposesample

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.remote.core.CoreDocument
import androidx.compose.remote.core.RemoteClock
import androidx.compose.remote.core.RemoteComposeBuffer
import androidx.compose.remote.core.operations.Header
import androidx.compose.remote.core.operations.RootContentBehavior
import androidx.compose.remote.player.compose.ExperimentalRemotePlayerApi
import androidx.compose.remote.player.compose.RemoteComposePlayerFlags
import androidx.compose.remote.player.compose.RemoteDocumentPlayer
import androidx.compose.remote.player.core.RemoteDocument
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.creator.createDocumentV1
import com.example.creator.createDocumentV2
import com.example.creator.examples.AnimationExample
import com.example.creator.examples.DataViewExample
import com.example.creator.examples.LazyImageExample
import com.example.creator.examples.RemoteImageExample
import com.example.creator.examples.RemoteScreen1
import com.example.creator.examples.TextExamples
import com.example.creator.examples.getRawBytesAnalogExample
import com.example.creator.examples.getRawBytesExample
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.io.ByteArrayInputStream
import kotlin.io.encoding.Base64
import kotlin.time.Duration
import kotlin.time.measureTimedValue

@OptIn(ExperimentalRemotePlayerApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun RemoteScreen(
    debugMode: Int = 0
) {
    val context = LocalContext.current
    RemoteComposePlayerFlags.shouldPlayerWrapContentSize = true

    val bitmapLoader = rememberBitmapLoader()

    val document by createDocumentV2 {
        AnimationExample()
    }.collectAsDocumentState()

    val document1 = RemoteDocument(getRawBytesExample()).document


    Column(
        modifier = Modifier.safeDrawingPadding()
    ) {
        document?.let { document ->
            RemoteDocumentPlayer(
                //modifier = Modifier.wrapContentSize(),
                document = document,
                documentWidth = document.width,
                documentHeight = document.height,
                debugMode = debugMode,
                onNamedAction = { action, value, stateUpdater ->
                    Toast.makeText(context, "$action: $value", Toast.LENGTH_SHORT).show()
                    Log.d("debugg", "onNamedAction action = $action, value = $value")
                },
                bitmapLoader = bitmapLoader
            )
        }
        Text("end")
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun RemoteScreen(byteArray: ByteArray) {
    val document = remember {
        RemoteDocument(byteArray).document
    }

    RemoteDocumentPlayer(
        document = document,
        documentWidth = document.width,
        documentHeight = document.height,
        onNamedAction = { action, value, stateUpdater ->
            Log.d("debug", "onNamedAction action = $action, value = $value")
        },
    )
}

@SuppressLint("RestrictedApi")
@Composable
fun Flow<ByteArray?>.collectAsDocumentState(): State<CoreDocument?> {
    val context: Context = LocalContext.current
    return remember {
        this
            .filterNotNull()
            .map { byteArray ->
                Log.d("debugg",byteArray.toList().toString())
                val (doc, duration) = measureTimedValue{ RemoteDocument(byteArray).document }
                //saveDocument(context, duration, byteArray)
                doc
            }
    }.collectAsState(null)
}

private fun saveDocument(context: Context, duration: Duration, byteArray: ByteArray) {
    Log.d("debuggg", "collectAsDocumentState duration = $duration: ${Base64.encode(byteArray)}")
    val timestamp = java.text.SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.getDefault()).format(java.util.Date())
    val filename = "document_$timestamp.txt"

    val contentValues = ContentValues().apply {
        put(MediaStore.Downloads.DISPLAY_NAME, filename)
        put(MediaStore.Downloads.MIME_TYPE, "text/plain")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Downloads.IS_PENDING, 1)
        }
    }

    val uri = context.contentResolver.insert(
        MediaStore.Downloads.EXTERNAL_CONTENT_URI,
        contentValues
    )!!

    try {
        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            outputStream.write(Base64.encode(byteArray).toByteArray())
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.clear()
            contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
            context.contentResolver.update(uri, contentValues, null, null)
        }
    } catch (e: Exception) {
        Log.e("debuggg", "Failed to write file $filename: ${e.message}")
    }
}
