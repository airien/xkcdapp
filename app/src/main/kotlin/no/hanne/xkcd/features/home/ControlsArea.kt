package no.hanne.xkcd.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.FastForward
import androidx.compose.material.icons.rounded.FastRewind
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import no.hanne.xkcd.core.ui.components.AppRoundIconButton

@Composable
fun ControlsArea(
    modifier: Modifier = Modifier,
    isFirstEnabled: Boolean,
    isPreviousEnabled: Boolean,
    isNextEnabled: Boolean,
    isLastEnabled: Boolean,
    onFirst: () -> Unit,
    onPrevious: () -> Unit,
    onRandom: () -> Unit,
    onNext: () -> Unit,
    onLast: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AppRoundIconButton(
            Rounded.FastRewind,
            tint = if (isPreviousEnabled) Color.Black else Color.White,
            borderColor = if (isFirstEnabled) Color.Black else Color.White
        ) {
            if (isFirstEnabled) onFirst()
        }
        AppRoundIconButton(
            Rounded.SkipPrevious,
            tint = if (isPreviousEnabled) Color.Black else Color.White,
            borderColor = if (isPreviousEnabled) Color.Black else Color.White
        ) {
            if (isPreviousEnabled) onPrevious()
        }
        AppRoundIconButton(
            Rounded.Shuffle
        ) {
            onRandom()
        }
        AppRoundIconButton(
            Rounded.SkipNext,
            tint = if (isNextEnabled) Color.Black else Color.White,
            borderColor = if (isNextEnabled) Color.Black else Color.White
        ) {
            if (isNextEnabled) onNext()
        }
        AppRoundIconButton(
            Rounded.FastForward,
            tint = if (isLastEnabled) Color.Black else Color.White,
            borderColor = if (isLastEnabled) Color.Black else Color.White
        ) {
            if (isLastEnabled) onLast()
        }
    }
}