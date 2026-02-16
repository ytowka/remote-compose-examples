package com.example.newcomposesample

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalDensity
import com.example.creator.createDocumentV2
import com.example.creator.examples.ImageExample
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlin.io.encoding.Base64
import kotlin.time.measureTimedValue

@SuppressLint("RestrictedApi")
@Composable
fun RemoteScreen(
    debugMode: Int = 2
) {
    val bitmapLoader = rememberBitmapLoader()

    val document by createDocumentV2 {
        ImageExample()
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
    return remember {
        this
            .filterNotNull()
            .map {
                val (doc, duration) = measureTimedValue{ RemoteDocument(it).document }
                Log.d("debuggg", "collectAsDocumentState duration = $duration: ${Base64.encode(it)}")
                doc
            }
    }.collectAsState(null)
}