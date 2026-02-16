package com.example.creator.examples

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.remote.creation.compose.layout.RemoteCanvas
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteImage
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberRemoteBitmap
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.ImageLoader
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.toBitmap
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun RemoteImageExample() {
    RemoteColumn(
        modifier = RemoteModifier
            //.background(color = Color.Gray)
            .padding(all = 16.rf)
            .fillMaxSize(),
    ) {

        val context = LocalContext.current
        val bitmapLoader: (url: String) -> Bitmap = remember {
            val imageLoader = ImageLoader.Builder(context)
                .build()

            val getBitMap: (url: String) -> Bitmap = bitmapLoader@{ url ->
                val request = ImageRequest.Builder(context)
                    .data(url)
                    .build()
                val result = runBlocking {
                    imageLoader.execute(request)
                }

                Log.d("debuggg", "loadBitmap() called with: url = $url, result = ${result.image?.height}")
                return@bitmapLoader when(result) {
                    is ErrorResult -> throw Exception()
                    is SuccessResult -> {
                        result.image.toBitmap()
                    }
                }
            }
            getBitMap
        }
        RemoteImage(
            modifier = RemoteModifier
                .size(100.rdp),
            bitmap = bitmapLoader("https://raw.githubusercontent.com/test-images/png/refs/heads/main/202105/cs-black-000.png").asImageBitmap(),
            contentDescription = null
        )
    }
}