package com.example.creator.examples

import android.annotation.SuppressLint
import androidx.compose.remote.creation.compose.action.ValueChange
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteCollapsibleColumn
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.animationSpec
import androidx.compose.remote.creation.compose.modifier.fillMaxSize
import androidx.compose.remote.creation.compose.modifier.graphicsLayer
import androidx.compose.remote.creation.compose.modifier.rememberRemoteScrollState
import androidx.compose.remote.creation.compose.modifier.verticalScroll
import androidx.compose.remote.creation.compose.modifier.visibility
import androidx.compose.remote.creation.compose.state.animateRemoteFloat
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberMutableRemoteFloat
import androidx.compose.remote.creation.compose.state.rememberRemoteIntValue
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.ri
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.creator.RemoteButton
import com.example.creator.RemoteSpacer

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun AnimationExample() {
    val alpha = rememberMutableRemoteFloat { 0.rf } // rememberMutableRemoteFloat не работает
    val visibility = rememberRemoteIntValue { 0 }
    val alphaI = rememberRemoteIntValue { 0 }

    val opacity = animateRemoteFloat(alphaI.toRemoteFloat(), duration = 1f, initialValue = 0f)
    RemoteColumn(
        modifier = RemoteModifier
            .fillMaxSize()
            .verticalScroll(rememberRemoteScrollState())
            .fillMaxSize(),
        verticalArrangement = RemoteArrangement.Center,
        horizontalAlignment = RemoteAlignment.CenterHorizontally,
    ) {
        RemoteSpacer(12.rdp)
        RemoteText(
            modifier = RemoteModifier
                .visibility(visibility)
                .graphicsLayer(alpha = opacity),
            text = "animated",
            fontSize = 20.sp
        )
        RemoteSpacer(12.rdp )
        RemoteButton(
            text = "animate: ".rs + opacity.toRemoteString(before = 30),
            actions = listOf(
               /*
               Не работает из-за бага библиотеки. Можно проверить работу, если в CoreDocument.java с
               помощью дебаггера подменить IntegerExpression на нужный на 475 строке
               ValueChange(
                    alphaI,
                    selectIfGE(alphaI, 1.ri, 0.ri, 1.ri)
                ),*/
                ValueChange(alphaI, 1.ri),
                ValueChange(visibility, 1.ri),
            )
        )
    }
}