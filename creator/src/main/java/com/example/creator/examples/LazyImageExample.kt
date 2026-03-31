package com.example.creator.examples

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteCanvas
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteImage
import androidx.compose.remote.creation.compose.layout.RemoteSize
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.drawWithContent
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.painter.RemoteBitmapPainter
import androidx.compose.remote.creation.compose.painter.painterRemoteBitmap
import androidx.compose.remote.creation.compose.state.RemoteBitmap
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberNamedRemoteBitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun LazyImageExample() {
    RemoteColumn {
        // Статическое изображение, bitmap сохраняется в документ
        val staticBitmap = remember { createBitmap(10, 10)
            .apply { eraseColor(android.graphics.Color.GREEN) }
            .asImageBitmap()
        }

        RemoteImage(
            modifier = RemoteModifier
                .size(100.rdp),
            bitmap = staticBitmap,
            contentDescription = null
        )

        // Динамичкская подгрузка изображения на клиенте
        val dynamicBitmap = rememberNamedRemoteBitmap(
            name = "image",
            url = "https://raw.githubusercontent.com/test-images/png/refs/heads/main/202105/cs-black-000.png",
        )

        RemoteImage(
            modifier = RemoteModifier.size(100.rdp),
            bitmap = dynamicBitmap
        )
    }
}

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
// Существующий RemoteImage не работает из-за бага
fun RemoteImage(modifier: RemoteModifier, bitmap: RemoteBitmap) {
    RemoteCanvas(modifier) {
        drawScaledBitmap(bitmap, srcSize = size)
    }
}