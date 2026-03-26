package com.example.creator

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.remote.core.operations.DrawTextOnCircle
import androidx.compose.remote.creation.RemotePath
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
import androidx.compose.remote.creation.compose.modifier.background
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
import androidx.compose.remote.creation.compose.state.creationState
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.rsp
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.toAndroidRectF
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
            .background(color = color,/* 12.rdp*/)
            .clickable(actions = actions),
        verticalAlignment = RemoteAlignment.CenterVertically,
        horizontalArrangement = RemoteArrangement.Center
    ) {

        RemoteSpacer(paddings)
        RemoteColumn(
            horizontalAlignment = RemoteAlignment.CenterHorizontally,
            verticalArrangement = RemoteArrangement.Center
        ) {

            RemoteSpacer(paddings)
            RemoteText(text = text, fontSize = 20.rsp, color= RemoteColor(Color.BLACK))
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
            this.color = color
        }
        val cornerRadiusPx = remoteDensity.density * cornerRadius.value
        drawRoundRect(paint, cornerRadius = RemoteOffset(cornerRadiusPx,cornerRadiusPx))
        drawContent()
    }
}

@Composable
@RemoteComposable
@SuppressLint("RestrictedApi")
fun RemoteModifier.background(brush: RemoteBrush, cornerRadius: RemoteDp): RemoteModifier {
    return this.drawWithContent {
        val paint = RemotePaint()
        with(brush) {
            applyTo(paint, size)
        }
        val cornerRadiusPx = remoteDensity.density * cornerRadius.value
        drawRoundRect(paint, cornerRadius = RemoteOffset(cornerRadiusPx, cornerRadiusPx))
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
            end = RemoteOffset(width, height)
        )
        val paint = RemotePaint()
        with(brush) {
          applyTo(paint, size)
        }
        val cornerRadiusPx = remoteDensity.density * cornerRadius.value
        drawRoundRect(paint, cornerRadius = RemoteOffset(cornerRadiusPx, cornerRadiusPx))
        drawContent()
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
            end = RemoteOffset(width, height)
        )
        val paint = RemotePaint {
            this.textSize = 120.rf
            style = PaintingStyle.Stroke
            this.strokeWidth = 3.rf
        }
        with(brush) {
            applyTo(paint, size)
        }

        drawText(
            text = text,
            paint = paint,
            x = 0.rf,
            y = height
        )
    }
}