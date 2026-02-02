package com.example.newcomposesample

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.remote.player.compose.RemoteDocumentPlayer
import androidx.compose.remote.player.core.RemoteDocument
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import coil3.ImageLoader
import com.example.creator.createDocumentV1
import com.example.creator.examples.StateChangeExample
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

@SuppressLint("RestrictedApi")
@Composable
fun RemoteScreen(
    debugMode: Int = 2
) {
    val context = LocalContext.current

    val bitmapLoader = remember {
        val imageLoader = ImageLoader.Builder(context)
            .build()

        CoilBitmapLoader(context, imageLoader)
    }

    val bytes by createDocumentV1 { StateChangeExample() }
    val document by remember {
        snapshotFlow { bytes }
            .filterNotNull()
            .map { RemoteDocument(it).document }
    }.collectAsState(null)

    BoxWithConstraints(
        modifier = Modifier.safeDrawingPadding()
    ) {
        document?.let { document ->
            RemoteDocumentPlayer(
                document = document,
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