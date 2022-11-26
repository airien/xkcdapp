package no.hanne.xkcd.features.home

import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.FastForward
import androidx.compose.material.icons.rounded.FastRewind
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import no.hanne.xkcd.core.ui.components.AppRoundIconButton
import no.hanne.xkcd.core.ui.theme.Gray500

@Composable
fun ControlsArea(
    modifier: Modifier = Modifier,
    isFirstEnabled: Boolean,
    isPreviousEnabled: Boolean,
    isNextEnabled: Boolean,
    isLastEnabled: Boolean,
    onFirst: () -> Unit,
    onPrevious: () -> Unit,
    onMore: () -> Unit,
    onNext: () -> Unit,
    onLast: () -> Unit,
) {
    FlowRow(
        modifier = modifier,
        crossAxisSpacing = 16.dp,
        mainAxisSpacing = 16.dp,
        crossAxisAlignment = FlowCrossAxisAlignment.Center,
        mainAxisAlignment = FlowMainAxisAlignment.Center
    ) {
        AppRoundIconButton(
            icon = Rounded.SkipPrevious,
            tint = if (isPreviousEnabled) Color.Black else Gray500,
            borderColor = if (isFirstEnabled) Color.Black else Gray500
        ) {
            if (isFirstEnabled) onFirst()
        }
        AppRoundIconButton(
            icon = Rounded.FastRewind,
            tint = if (isPreviousEnabled) Color.Black else Gray500,
            borderColor = if (isPreviousEnabled) Color.Black else Gray500
        ) {
            if (isPreviousEnabled) onPrevious()
        }
        AppRoundIconButton(
            icon = Rounded.MoreHoriz
        ) {
            onMore()
        }
        AppRoundIconButton(
            icon = Rounded.FastForward,
            tint = if (isNextEnabled) Color.Black else Gray500,
            borderColor = if (isNextEnabled) Color.Black else Gray500
        ) {
            if (isNextEnabled) onNext()
        }
        AppRoundIconButton(
            icon = Rounded.SkipNext,
            tint = if (isLastEnabled) Color.Black else Gray500,
            borderColor = if (isLastEnabled) Color.Black else Gray500
        ) {
            if (isLastEnabled) onLast()
        }
    }
}