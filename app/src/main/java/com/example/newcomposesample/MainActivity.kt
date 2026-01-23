package com.example.newcomposesample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.remote.core.CoreDocument
import androidx.compose.remote.player.compose.RemoteDocumentPlayer
import androidx.compose.remote.player.core.RemoteDocument
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.example.creator.ExampleScreen
import com.example.newcomposesample.ui.theme.NewComposeSampleTheme
import kotlinx.coroutines.flow.flow

class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewComposeSampleTheme {
                val context = LocalContext.current

                var document by remember { mutableStateOf<CoreDocument?>(null) }

                LaunchedEffect(Unit) {
                    val bytes = ExampleScreen.createDocument(context)
                    Log.d("debugg", "onCreate() called ${bytes.size}")
                    document = RemoteDocument(bytes).document
                }

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