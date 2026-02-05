package com.example.creator.examples

import android.annotation.SuppressLint
import androidx.compose.material3.LocalTextStyle
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.fillMaxWidth
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.state.RemoteColor
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.creator.RemoteSpacer

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun TextExamples() {
    RemoteColumn(
        modifier = RemoteModifier
            .background(color = Color.Gray)
            .padding(all = 16.rf)
            .fillMaxSize(),
    ) {
        RemoteText(
            //modifier = RemoteModifier.visibility(intState),
            text = "LocalTextStyle Text",
            modifier = RemoteModifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None
                ),
                lineHeight = 36.sp
            )
        )
        RemoteSpacer(12.rdp)
        RemoteText(
            //modifier = RemoteModifier.visibility(intState),
            text = "Styled Text",
            modifier = RemoteModifier.fillMaxWidth(),
            color = RemoteColor(Color(0xFF910B00)),
            fontSize = 26.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Clip,
            maxLines = Int.MAX_VALUE,
            style = LocalTextStyle.current,
        )
    }
}