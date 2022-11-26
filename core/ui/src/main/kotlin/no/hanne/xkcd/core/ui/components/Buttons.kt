package no.hanne.xkcd.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import no.hanne.xkcd.core.ui.R
import no.hanne.xkcd.core.ui.theme.BodyText
import no.hanne.xkcd.core.ui.theme.SemiTransparentLight
import no.hanne.xkcd.core.ui.theme.XKCDAppTheme

object AppButtonDefaults {
    val MinWidth = 78.dp
    val MinHeight = 52.dp
}

@Composable
fun AppButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(percent = 35),
        enabled = enabled,
        modifier = modifier
            .heightIn(min = AppButtonDefaults.MinHeight)
            .widthIn(min = AppButtonDefaults.MinWidth)
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(CenterVertically)
                    .size(24.dp)
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.button
        )

        if (loading) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(40.dp)
            )
        }
    }
}

@Composable
fun AppTextButton(
    modifier: Modifier = Modifier,
    text: String = "",
    labelModifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.primary,
    shape: Shape = MaterialTheme.shapes.small,
    onClick: () -> Unit
) {
    TextButton(
        colors = ButtonDefaults.textButtonColors(
            contentColor = contentColor,
            backgroundColor = backgroundColor
        ),
        modifier = modifier
            .heightIn(min = AppButtonDefaults.MinHeight)
            .widthIn(min = AppButtonDefaults.MinWidth),
        enabled = enabled,
        onClick = onClick,
        shape = shape
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            modifier = labelModifier,
            textAlign = textAlign
        )
    }
}

@Composable
fun AppRowContentButton(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.primary,
    shape: Shape = MaterialTheme.shapes.small,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    onClick: () -> Unit
) {
    TextButton(
        colors = ButtonDefaults.textButtonColors(
            contentColor = contentColor,
            backgroundColor = backgroundColor
        ),
        modifier = modifier
            .heightIn(min = AppButtonDefaults.MinHeight)
            .widthIn(min = AppButtonDefaults.MinWidth),
        enabled = enabled,
        onClick = onClick,
        shape = shape,
        contentPadding = contentPadding
    ) {
        content()
    }
}

@Composable
fun AppIconTextButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean = false,
    borderWidth: Dp = 1.dp,
    borderColor: Color = SemiTransparentLight,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = BodyText,
    shape: Shape = RoundedCornerShape(percent = 50),
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.textButtonColors(
            contentColor = contentColor,
            backgroundColor = backgroundColor
        ),
        border = BorderStroke(
            width = borderWidth,
            color = borderColor
        ),
        onClick = onClick,
        shape = shape,
        enabled = enabled,
        modifier = modifier
            .heightIn(min = AppButtonDefaults.MinHeight)
            .widthIn(min = AppButtonDefaults.MinWidth)
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(CenterVertically)
                    .size(24.dp)
            )
        }
        Icon(
            painterResource(id = icon),
            null,
            tint = contentColor,
            modifier = Modifier
                .padding(start = 5.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.caption,
            color = contentColor,
            modifier = Modifier.padding(start = 8.dp)
        )

        if (loading) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(40.dp)
            )
        }
    }
}

@Preview(backgroundColor = 0xFFFFFF)
@Composable
fun AppIconTextButtonPreview() {
    XKCDAppTheme() {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AppIconTextButton(text = "Filter", icon = R.drawable.giphy, onClick = { })
        }
    }
}

@Preview(backgroundColor = 0xFFFFFF)
@Composable
fun AppButtonPreview() {
    XKCDAppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AppButton(text = "Enabled", onClick = {})

            AppButton(text = "Disabled", onClick = {}, enabled = false)

            AppButton(text = "Loading", onClick = {}, loading = true)
        }
    }
}

@Preview(backgroundColor = 0xFFFFFF)
@Composable
fun AppTextButtonPreview() {
    XKCDAppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AppTextButton(text = "Enabled", onClick = {})

            AppTextButton(text = "Disabled", enabled = false, onClick = {})
            AppTextButton(
                text = "Start",
                enabled = false,
                onClick = {},
                modifier = Modifier.width(300.dp),
                labelModifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun AppContentButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    content: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(percent = 35),
        enabled = enabled,
        modifier = modifier
            .heightIn(min = AppButtonDefaults.MinHeight)
            .widthIn(min = AppButtonDefaults.MinWidth)
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(CenterVertically)
                    .size(24.dp)
            )
        }

        content()

        if (loading) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(40.dp)
            )
        }
    }
}

@Composable
fun AppRoundIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    imageWidthDp: Dp = 20.dp,
    borderWidth: Dp = 1.dp,
    borderColor: Color = MaterialTheme.colors.primary,
    tint: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = Color.Transparent,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier then Modifier
            .clip(shape = CircleShape)
            .background(color = backgroundColor)
            .size((imageWidthDp.value + 16).dp)
            .border(BorderStroke(borderWidth, borderColor), shape = CircleShape)
    ) {
        Icon(
            tint = tint,
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Center)
                .size(imageWidthDp)
                .clickable(onClick = { onClick() })
        )
    }
}

@Preview(backgroundColor = 0xFFFFFF)
@Composable
fun AppRoundIconButtonPreview() {
    XKCDAppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AppRoundIconButton(icon = Icons.Rounded.Videocam)
            AppRoundIconButton(icon = Icons.Rounded.Mic)
        }
    }
}
