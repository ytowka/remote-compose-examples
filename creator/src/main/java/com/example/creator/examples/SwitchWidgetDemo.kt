package com.example.creator.examples

import android.annotation.SuppressLint
import androidx.compose.remote.core.operations.layout.Component
import androidx.compose.remote.core.operations.layout.managers.StateLayout
import androidx.compose.remote.creation.compose.action.ValueChange
import androidx.compose.remote.creation.compose.layout.RemoteAlignment
import androidx.compose.remote.creation.compose.layout.RemoteArrangement
import androidx.compose.remote.creation.compose.layout.RemoteBox
import androidx.compose.remote.creation.compose.layout.RemoteCanvas
import androidx.compose.remote.creation.compose.layout.RemoteColumn
import androidx.compose.remote.creation.compose.layout.RemoteComposable
import androidx.compose.remote.creation.compose.layout.RemoteRow as Row
import androidx.compose.remote.creation.compose.layout.RemoteText
import androidx.compose.remote.creation.compose.layout.StateMachineSpec
import androidx.compose.remote.creation.compose.modifier.RemoteModifier as Modifier
import androidx.compose.remote.creation.compose.modifier.RemoteModifier
import androidx.compose.remote.creation.compose.modifier.background
import androidx.compose.remote.creation.compose.modifier.clickable
import androidx.compose.remote.creation.compose.modifier.clip
import androidx.compose.remote.creation.compose.modifier.fillMaxWidth
import androidx.compose.remote.creation.compose.modifier.padding
import androidx.compose.remote.creation.compose.modifier.size
import androidx.compose.remote.creation.compose.modifier.visibility
import androidx.compose.remote.creation.compose.modifier.wrapContentSize
import androidx.compose.remote.creation.compose.shapes.RemoteRoundedCornerShape
import androidx.compose.remote.creation.compose.state.MutableRemoteInt
import androidx.compose.remote.creation.compose.state.RemoteInt
import androidx.compose.remote.creation.compose.state.RemotePaint
import androidx.compose.remote.creation.compose.state.rc
import androidx.compose.remote.creation.compose.state.rdp
import androidx.compose.remote.creation.compose.state.rememberMutableRemoteInt
import androidx.compose.remote.creation.compose.state.rf
import androidx.compose.remote.creation.compose.state.ri
import androidx.compose.remote.creation.compose.state.rs
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun SwitchWidgetOnState(modifier: RemoteModifier = RemoteModifier, id: Int = 0) {
    RemoteBox(
        modifier =
            modifier
                .clip(RemoteRoundedCornerShape(20.rdp))
                .background(Color(63, 81, 181, 255))
                .padding(2.rdp),
        contentAlignment = RemoteAlignment.CenterEnd,
    ) {
        RemoteCanvas(modifier = RemoteModifier.size(32.rdp)) {
            val paint = RemotePaint().apply { color = Color(255, 255, 255).rc }
            drawCircle(paint = paint, radius = 34f.rf)
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun SwitchWidgetOffState(modifier: RemoteModifier = RemoteModifier) {
    RemoteBox(
        modifier =
            modifier
                // todo: use the animationId
                .clip(RemoteRoundedCornerShape(20.rdp))
                .background(Color(100, 100, 100))
                .padding(8.rdp)
                .then(modifier),
        contentAlignment = RemoteAlignment.CenterStart,
    ) {
        RemoteCanvas(modifier = RemoteModifier.size(20.rdp)) {
            val paint = RemotePaint().apply { color = Color(220, 220, 220).rc }
            drawCircle(paint = paint, radius = 34f.rf)
        }
    }
}

@Composable
@RemoteComposable
fun RemoteComponent(name: String, content: @Composable @RemoteComposable () -> Unit) {
    content()
}

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun SwitchWidget(value: MutableRemoteInt) {
    val modifier =
        RemoteModifier.clickable(
            ValueChange(remoteState = value, updatedValue = (value + 1) % 2)
        )

    RemoteBox(
        modifier = RemoteModifier.padding(4.rdp),
        contentAlignment = RemoteAlignment.CenterStart,
    ) {
        val modifierSize = RemoteModifier.size(60.rdp, 36.rdp)
        androidx.compose.remote.creation.compose.layout.StateLayout(StateMachineSpec(
           value, listOf(0,1)
        )) { state ->
            when (state) {
                0 -> SwitchWidgetOffState(modifier = modifierSize)
                1 -> SwitchWidgetOnState(modifier = modifierSize)
            }
        }
        RemoteBox(modifier = modifierSize.clip(RemoteRoundedCornerShape(20.rdp)).then(modifier))
    }
}

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun RowSwitch(
    state: MutableRemoteInt,
    label: String,
    modifier: RemoteModifier = RemoteModifier,
) {
    Row(modifier = modifier, verticalAlignment = RemoteAlignment.CenterVertically) {
        RemoteText(label)
        SwitchWidget(state)
        RemoteText("State value is ")
        RemoteText(state.toRemoteString(10))
    }
}

@SuppressLint("RestrictedApi")
@Composable
@RemoteComposable
fun StateInfo(
    state: RemoteInt,
    label: String,
    modifier: RemoteModifier = RemoteModifier,
) {
    Row(modifier = modifier, verticalAlignment = RemoteAlignment.CenterVertically) {
        RemoteText(label)
        RemoteText(state.toRemoteString(10))
    }
}

@Composable
@RemoteComposable
fun Divider(modifier: RemoteModifier = RemoteModifier) {
    RemoteBox(
        modifier =
            modifier
                .padding(left = 8.rdp, right = 8.rdp)
                .size(2.rdp, 8.rdp)
                .background(Color.LightGray)
    )
}

@Composable
@RemoteComposable
fun SwitchWidgetDemo() {
    RemoteColumn(modifier = Modifier.padding(8.rdp).background(Color.LightGray)) {
        val checkedA = rememberMutableRemoteInt(0)
        val checkedB = rememberMutableRemoteInt(0)
        val checkedC = rememberMutableRemoteInt(1)

        val visibilityModifierC = RemoteModifier.visibility(checkedC)
        RowSwitch(checkedA, "State A")
        RowSwitch(checkedB, "State B", modifier = visibilityModifierC)
        RowSwitch(checkedA, "State A", modifier = visibilityModifierC)
        RowSwitch(checkedC, "State C")
        Row(
            modifier = Modifier.padding(top = 8.rdp).fillMaxWidth(),
            horizontalArrangement = RemoteArrangement.Center,
            verticalAlignment = RemoteAlignment.CenterVertically,
        ) {
            val visibilityModifierB = RemoteModifier.visibility(checkedB)
            StateInfo(checkedA, "A is ")
            Divider(modifier = visibilityModifierB)
            StateInfo(checkedB, "B is ", modifier = visibilityModifierB)
            Divider()
            StateInfo(checkedC, "C is ")
        }
    }
}
