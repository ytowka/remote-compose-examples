package com.example.newcomposesample

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.remote.player.compose.RemoteDocumentPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.example.creator.createDocumentV1
import com.example.creator.examples.HostActionExample
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun RemoteDynamicScreenExample() {

    val buttons = remember {
        List(10) { "button $it" }
    }
    var selected by remember { mutableIntStateOf(0) }

    val context = LocalContext.current

    val document by remember {
        snapshotFlow { selected }.flatMapLatest { selectedId ->
            flow {
                val doc = createDocumentV1(context) {
                    HostActionExample(buttons, selectedId)
                }
                emit(doc)
            }
        }
    }.collectAsDocumentState()


    BoxWithConstraints(
        modifier = Modifier.safeDrawingPadding()
    ) {
        document?.let { document ->
            RemoteDocumentPlayer(
                document = document,
                documentWidth = with(LocalDensity.current){ maxWidth.roundToPx() },
                documentHeight = with(LocalDensity.current){ maxHeight.roundToPx() },
                modifier = Modifier.fillMaxSize(),
                onNamedAction = { action, value, stateUpdater ->
                    if (action == "buttonClick") {
                        selected = value as Int
                    }
                },
            )
        }
    }
}