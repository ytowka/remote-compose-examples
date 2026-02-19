package com.example.newcomposesample

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.remote.core.CoreDocument
import androidx.compose.remote.core.operations.RootContentBehavior
import androidx.compose.remote.player.compose.RemoteDocumentPlayer
import androidx.compose.remote.player.core.RemoteDocument
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.example.creator.createDocumentV2
import com.example.creator.examples.DataViewExample
import com.example.creator.examples.ImageExample
import com.example.creator.examples.LargeExample
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.io.File
import kotlin.io.encoding.Base64
import kotlin.time.measureTimedValue

@SuppressLint("RestrictedApi")
@Composable
fun RemoteScreen(
    debugMode: Int = 0
) {
    val bitmapLoader = rememberBitmapLoader()

    val document by createDocumentV2 {
        LargeExample()
    }.collectAsDocumentState()


    BoxWithConstraints(
        modifier = Modifier.safeDrawingPadding()
    ) {
        document?.let { document ->
            RemoteDocumentPlayer(
                document = document.also {
                    it.setRootContentBehavior(
                        RootContentBehavior.SCROLL_NONE,
                        RootContentBehavior.ALIGNMENT_TOP,
                        RootContentBehavior.SIZING_LAYOUT,
                        RootContentBehavior.LAYOUT_WRAP_CONTENT
                    )
                },
                documentWidth = with(LocalDensity.current){ maxWidth.roundToPx() },
                documentHeight = with(LocalDensity.current){ maxHeight.roundToPx() },
                modifier = Modifier.fillMaxSize(),
                debugMode = debugMode,
                onNamedAction = { action, value, stateUpdater ->
                    //Toast.makeText(context, "$action: $value", Toast.LENGTH_SHORT).show()
                    Log.d("debugg", "onNamedAction action = $action, value = $value")
                },
                bitmapLoader = bitmapLoader
            )
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun Flow<ByteArray?>.collectAsDocumentState(): State<CoreDocument?> {
    val context: Context = LocalContext.current
    return remember {
        this
            .filterNotNull()
            .map { byteArray ->
                val (doc, duration) = measureTimedValue{ RemoteDocument(byteArray).document }
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
                )

                uri?.let {
                    try {
                        context.contentResolver.openOutputStream(it)?.use { outputStream ->
                            outputStream.write(Base64.encode(byteArray).toByteArray())
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            contentValues.clear()
                            contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
                            context.contentResolver.update(it, contentValues, null, null)
                        }
                    } catch (e: Exception) {
                        Log.e("debuggg", "Failed to write file $filename: ${e.message}")
                    }
                }
                doc
            }
    }.collectAsState(null)
}
