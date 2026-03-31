package com.example.newcomposesample

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.remote.core.CoreDocument
import androidx.compose.remote.player.compose.ExperimentalRemotePlayerApi
import androidx.compose.remote.player.compose.RemoteDocumentPlayer
import androidx.compose.remote.player.core.RemoteDocument
import androidx.compose.remote.player.core.platform.BitmapLoader
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.creator.createDocumentV1
import com.example.creator.examples.AnimationSimpleExample
import com.example.creator.examples.getBasicDocument
import com.example.creator.examples.getRawBytesExample
import java.io.ByteArrayInputStream
import java.io.InputStream

fun loadBitmap(url: String): InputStream {
    // Любая реализация функции, которая по идентификатору
    // вернет bitmap в inputStream формате
    return ByteArrayInputStream(byteArrayOf())
}

@OptIn(ExperimentalRemotePlayerApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun RemoteSimpleScreen() {
    val context = LocalContext.current

    val document by produceState<CoreDocument?>(null) {
        val bytes = getRawBytesExample()
        value = RemoteDocument(bytes).document
    }

    document?.let { document ->
        RemoteDocumentPlayer(
            modifier = Modifier.fillMaxSize(),
            document = document,
            documentWidth = document.width,
            documentHeight = document.height,
            onNamedAction = { name, value, _ ->
                Toast.makeText(context, "$name: $value", Toast.LENGTH_SHORT).show()
            },
            bitmapLoader = remember {
                BitmapLoader { url ->
                    loadBitmap(url)
                }
            }
        )
    }
}