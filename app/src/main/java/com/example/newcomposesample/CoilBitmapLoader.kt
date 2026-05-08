package com.example.newcomposesample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.remote.player.core.platform.BitmapLoader
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import coil3.ImageLoader
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.toBitmap
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

private val resourceMap = mapOf(
    "glyph_chevron_right_m" to R.drawable.glyph_chevron_right_m
)


@SuppressLint("RestrictedApi")
class CoilBitmapLoader(
    val context: Context,
    val imageLoader: ImageLoader
) : BitmapLoader {
    val cache = mutableMapOf<String, InputStream>()

    override fun loadBitmap(url: String): InputStream {
        if (url.startsWith("resource://")) {
            val resourceName = url.removePrefix("resource://")
            val resId = resourceMap[resourceName]
            if(resourceName in cache) { return cache[resourceName]!! }
            return if (resId != null) {
                drawableToInputStream(ContextCompat.getDrawable(context, resId)).also {
                    cache[resourceName] = it
                }
            } else {
                Log.w("CoilBitmapLoader", "Resource not found: $resourceName")
                InputStream.nullInputStream()
            }
        }
        return InputStream.nullInputStream()

        val request = ImageRequest.Builder(context)
            .data(url)
            .build()
        val result = runBlocking {
            imageLoader.execute(request)
        }

        Log.d("debuggg", "loadBitmap() called with: url = $url, result = ${result.image?.height}")
        return when(result) {
            is ErrorResult -> InputStream.nullInputStream()
            is SuccessResult -> {
                val bitmap = result.image.toBitmap()
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
                ByteArrayInputStream(outputStream.toByteArray())
            }
        }
    }

    private fun drawableToInputStream(drawable: Drawable?): InputStream {
        if (drawable == null) return InputStream.nullInputStream()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth.coerceAtLeast(1),
            drawable.intrinsicHeight.coerceAtLeast(1),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
        return ByteArrayInputStream(outputStream.toByteArray())
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