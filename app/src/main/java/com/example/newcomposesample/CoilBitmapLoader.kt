package com.example.newcomposesample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import androidx.compose.remote.player.core.platform.BitmapLoader
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import coil3.ImageLoader
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.toBitmap
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

@SuppressLint("RestrictedApi")
class CoilBitmapLoader(
    val context: Context,
    val imageLoader: ImageLoader
) : BitmapLoader {
    override fun loadBitmap(url: String): InputStream {
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()
        val result = runBlocking {
            imageLoader.execute(request)
        }
        return when(result) {
            is ErrorResult -> throw Exception()
            is SuccessResult -> {
                val bitmap = result.image.toBitmap()

                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
                ByteArrayInputStream(outputStream.toByteArray())
            }
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun rememberBitmapLoader(): BitmapLoader {
    val context = LocalContext.current
    return remember {
        val imageLoader = ImageLoader.Builder(context)
            .build()

        CoilBitmapLoader(context, imageLoader)
    }
}