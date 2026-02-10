package com.example.creator

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.remote.core.operations.DrawTextOnCircle
import androidx.compose.remote.creation.compose.action.Action
import androidx.compose.remote.creation.compose.capture.RemoteDensity
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteCanvas
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteOffset
import androidx.compose.remote.creation.compose.layout.RemoteRow
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.drawWithContent
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.shaders.RemoteBrush
import androidx.compose.remote.creation.compose.shaders.RemoteLinearGradient
import androidx.compose.remote.creation.compose.state.RemoteColor
import androidx.compose.remote.creation.compose.state.RemoteDp
import androidx.compose.remote.creation.compose.state.RemotePaint
import androidx.compose.remote.creation.compose.state.RemoteStateScope
import androidx.compose.remote.creation.compose.state.RemoteString
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun RemoteSpacer(size: RemoteDp) {
    RemoteBox(modifier = RemoteModifier.size(size))
}

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun RemoteButton(
    modifier: RemoteModifier = RemoteModifier,
    color: RemoteColor = RemoteColor("#F2F3F5".toColorInt()),
    text: RemoteString,
    paddings: RemoteDp = 12.rdp,
    actions: List<Action> = emptyList()
) {
    RemoteRow(
        modifier = modifier
            .background(color = color, 12.rdp)
            .clickable(actions = actions),
        verticalAlignment = RemoteAlignment.CenterVertically,
        horizontalArrangement = RemoteArrangement.CenterHorizontally
    ) {

        RemoteSpacer(paddings)
        RemoteColumn(
            horizontalAlignment = RemoteAlignment.CenterHorizontally,
            verticalArrangement = RemoteArrangement.Center
        ) {

            RemoteSpacer(paddings)
            RemoteText(text = text, fontSize = 20.sp)
            RemoteSpacer(paddings)
        }
        RemoteSpacer(paddings)
    }
}

@Composable
@RemoteComposable
@SuppressLint("RestrictedApi")
fun RemoteModifier.background(color: RemoteColor, cornerRadius: RemoteDp): RemoteModifier {
    return this.drawWithContent {
        val paint = RemotePaint().apply {
            remoteColor = color
        }
        val size = RemoteDensity.HOST.density * cornerRadius.value
        drawRoundRect(RemotePaint(paint), cornerRadius = RemoteOffset(size, size))
        drawContent()
    }
}

@Composable
@RemoteComposable
@SuppressLint("RestrictedApi")
fun RemoteModifier.background(brush: RemoteBrush, cornerRadius: RemoteDp): RemoteModifier {
    return this.drawWithContent {
        val paint = RemotePaint().apply { applyRemoteBrush(brush, remoteSize) }
        val size = RemoteDensity.HOST.density * cornerRadius.value
        drawRoundRect(RemotePaint(paint), cornerRadius = RemoteOffset(size, size))
        drawContent()
    }
}

@Composable
@RemoteComposable
@SuppressLint("RestrictedApi")
fun RemoteModifier.gradientBackground(colors: List<RemoteColor>, cornerRadius: RemoteDp): RemoteModifier {
    return this.drawWithContent {
        val brush = RemoteLinearGradient(
            colors = colors,
            start = RemoteOffset(0f, 0f),
            end = RemoteOffset(remoteSize.width, remoteSize.height)
        )
        val paint = RemotePaint().apply { applyRemoteBrush(brush, remoteSize) }
        val size = RemoteDensity.HOST.density * cornerRadius.value
        drawRoundRect(RemotePaint(paint), cornerRadius = RemoteOffset(size, size))
        drawContent()
    }
}

@Composable
@RemoteComposable
@SuppressLint("RestrictedApi")
fun RemoteFancyText(modifier: RemoteModifier, text: RemoteString) {
    RemoteCanvas(modifier = modifier) {
        val brush = RemoteLinearGradient(
            colors = listOf(RemoteColor(androidx.compose.ui.graphics.Color.Blue), RemoteColor( androidx.compose.ui.graphics.Color.Red)),
            start = RemoteOffset(0f, 0f),
            end = RemoteOffset(remoteSize.width, remoteSize.height)
        )
        val paint = RemotePaint().apply {
            applyRemoteBrush(brush, remoteSize)
            textSize = 50f
            style = Paint.Style.FILL
            this.strokeWidth = 2f
        }

        val path = Path().apply {
            val size = remoteSize.asSize(this@RemoteCanvas)
            //this.arcTo(Rect(Offset.Zero, size), 0f, 90f, true)

            moveTo(size.width / 2f, 0f)         // Start at the top center
            lineTo(size.width, size.height)    // Draw a line to the bottom right
            lineTo(0f, size.height)            // Draw a line to the bottom left
            close()
        }

        drawPath(path, paint)

        /*drawTextOnPath(
            text = text,
            paint = paint,
            path = path,
        )*/
    }
}

@Composable
@RemoteComposable
@SuppressLint("RestrictedApi")
fun RemoteGradientText(modifier: RemoteModifier, text: RemoteString) {
    RemoteCanvas(modifier = modifier) {
        val brush = RemoteLinearGradient(
            colors = listOf(RemoteColor(androidx.compose.ui.graphics.Color.Blue), RemoteColor( androidx.compose.ui.graphics.Color.Red)),
            start = RemoteOffset(0f, 0f),
            end = RemoteOffset(remoteSize.width, remoteSize.height)
        )
        val paint = RemotePaint().apply {
            applyRemoteBrush(brush, remoteSize)
            this.textSize = 120f
            style = Paint.Style.STROKE
            this.strokeWidth = 3f
        }

        drawText(
            text = text,
            paint = paint,
            x = 0.rf,
            y = remoteSize.height
        )
    }
}