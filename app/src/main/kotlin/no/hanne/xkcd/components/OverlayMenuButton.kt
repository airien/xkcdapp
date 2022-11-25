package no.hanne.xkcd.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import no.hanne.xkcd.core.ui.theme.AppCard
import no.hanne.xkcd.core.ui.theme.Gray800
import no.hanne.xkcd.core.ui.theme.White

@Composable
fun OverlayMenuButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int?,
    selected: Boolean,
    iconSize: Dp,
    unselectedIconColor: Color = Gray800,
    unselectedBackgroundColor: Color = White,
    selectedIconColor: Color = White,
    selectedBackgroundColor: Color = AppCard,
    showOutline: Boolean = false,
    onClick: () -> Unit
) {
    var selectedInternal by remember {
        mutableStateOf(false)
    }
    selectedInternal = selected
    var pressed by remember {
        mutableStateOf(false)
    }
    val iconColor =
        when {
            !selectedInternal && !pressed -> {
                unselectedIconColor
            }
            !selectedInternal && pressed -> {
                unselectedBackgroundColor
            }
            selectedInternal && !pressed -> {
                selectedIconColor
            }
            else -> {
                selectedBackgroundColor
            }
        }
    val backgroundColor =
        when {
            !selectedInternal && !pressed -> {
                unselectedBackgroundColor
            }
            !selectedInternal && pressed -> {
                unselectedIconColor
            }
            selectedInternal && !pressed -> {
                selectedBackgroundColor
            }
            else -> {
                selectedIconColor
            }
        }
    var boxModifier = modifier
        .size(70.dp, 70.dp)
        .clip(CircleShape)
        .background(backgroundColor)
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    try {
                        pressed = true
                        awaitRelease()
                    } finally {
                        pressed = false
                    }
                },
                onTap = {
                    onClick()
                }
            )
        }
    if (showOutline) {
        boxModifier = boxModifier.then(
            Modifier.border(BorderStroke(1.dp, iconColor), CircleShape)
        )
    }
    Box(
        modifier = boxModifier
    ) {
        icon?.let {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.Center),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = iconColor
            )
        }
    }
}
