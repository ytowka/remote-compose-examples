package com.example.newcomposesample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.remote.player.compose.ExperimentalRemotePlayerApi
import androidx.compose.remote.player.compose.RemoteComposePlayerFlags
import androidx.compose.remote.player.compose.RemoteDocumentPlayer
import androidx.compose.remote.player.core.RemoteDocument
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.example.creator.createDocumentV2
import com.example.newcomposesample.ui.theme.NewComposeSampleTheme
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalRemotePlayerApi::class)
    @SuppressLint("RestrictedApi", "UnusedBoxWithConstraintsScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        RemoteComposePlayerFlags.isViewPlayerEnabled = false

        setContent {
            NewComposeSampleTheme {
                val bytes by createDocumentV2()
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
                            debugMode = 2,
                            onNamedAction = { action, value, stateUpdater ->
                                //Toast.makeText(context, "$action: $value", Toast.LENGTH_SHORT).show()
                                Log.d("debugg", "onNamedAction action = $action, value = $value")
                            }
                        )
                    }
                }
            }
        }
    }
}