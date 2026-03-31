package com.example.creator.examples

import android.annotation.SuppressLint
import android.graphics.Typeface
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteCanvas
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteRow
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.clip
import androidx.compose.remote.creation.compose.modifier.fillMaxHeight
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.fillMaxWidth
import androidx.compose.remote.creation.compose.modifier.graphicsLayer
import androidx.compose.remote.creation.compose.modifier.height
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.modifier.rememberRemoteScrollState
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.modifier.verticalScroll
import androidx.compose.remote.creation.compose.modifier.width
import androidx.compose.remote.creation.compose.shapes.RemoteRectangleShape
import androidx.compose.remote.creation.compose.shapes.RemoteRoundedCornerShape
import androidx.compose.remote.creation.compose.state.RemoteColor
import androidx.compose.remote.creation.compose.state.RemotePaint
import androidx.compose.remote.creation.compose.state.RemotePaint.Companion.invoke
import androidx.compose.remote.creation.compose.state.abs
import androidx.compose.remote.creation.compose.state.rc
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberNamedRemoteBoolean
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.remote.creation.compose.state.rsp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import java.text.DecimalFormat

@SuppressLint("UnrememberedMutableState", "RestrictedApi")
@Suppress("RestrictedApiAndroidX")
@Composable
@RemoteComposable
fun PagingDemo() {
    val numElements = 12
    val scrollState = rememberRemoteScrollState()

    val dimensionCard = 280.rdp
    val decimalFormat = remember { DecimalFormat("####0.00") }

    scrollState.positionState

    val endReached = scrollState.positionState.

    RemoteColumn(
        modifier =
            RemoteModifier.fillMaxWidth()
                .clickable()
                .fillMaxSize()
                .clip(RemoteRectangleShape)
                .verticalScroll(scrollState),
        verticalArrangement = RemoteArrangement.Center,
        horizontalAlignment = RemoteAlignment.CenterHorizontally,
    ) {
        repeat(numElements) {
            RemoteBox(
                modifier =
                    RemoteModifier
                        .fillMaxWidth()
                        .height(96.rdp)
                        .padding(12.rdp),
                contentAlignment = RemoteAlignment.Center
            ) {
                RemoteText(text = "item: $it")
            }
        }
    }
}