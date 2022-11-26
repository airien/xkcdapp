package no.hanne.xkcd.core.ui.components // ktlint-disable filename

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import no.hanne.xkcd.core.ui.theme.Gray600
import no.hanne.xkcd.core.ui.theme.SemiTransparentDark
import kotlin.math.max
import kotlin.math.roundToInt

@SuppressLint("UnusedTransitionTargetStateParameter")
@Suppress("UnusedPrivateMember")
@Composable
fun PopUpDialog(
    modifier: Modifier = Modifier,
    visible: Boolean,
    showBackdrop: Boolean = true,
    background: Color = MaterialTheme.colors.background,
    shape: Shape = RoundedCornerShape(percent = 5),
    hideDialog: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val travelled = remember { mutableStateOf(0f) }
    LaunchedEffect(
        key1 = visible,
        block = {
            if (visible) {
                travelled.value = 0f
            }
        }
    )
    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (showBackdrop) SemiTransparentDark else Color.Transparent)
                .clickable { hideDialog() }
        )
    }
    AnimatedVisibility(
        modifier = modifier
            .offset(
                offset = {
                    IntOffset(
                        x = 0,
                        y = max(0, travelled.value.roundToInt())
                    )
                }
            )
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = {
                        travelled.value = 0f
                    },
                    onDragEnd = {
                        if (travelled.value > 100f) hideDialog()
                    }
                ) { _, dragAmount ->
                    travelled.value = travelled.value + dragAmount
                }
            },
        visible = visible,
        enter = slideInVertically(initialOffsetY = { screenHeight }) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { it + screenHeight }
        ) + fadeOut()
    ) {
        Card(
            backgroundColor = background,
            elevation = 8.dp,
            shape = shape
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.Transparent)
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                Card(
                    backgroundColor = Gray600,
                    elevation = 8.dp,
                    shape = RoundedCornerShape(percent = 50),
                    modifier = Modifier
                        .width(30.dp)
                        .height(4.dp)
                ) {}
                content()
            }
        }
    }
}
