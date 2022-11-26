package no.hanne.xkcd.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.FastForward
import androidx.compose.material.icons.rounded.FastRewind
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material.icons.rounded.Stars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onRandom: () -> Unit,
    onNext: () -> Unit,
    onLast: () -> Unit,
    onSearch: () -> Unit,
    onFavourite: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
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
            icon = Rounded.Shuffle
        ) {
            onRandom()
        }
        AppRoundIconButton(
            icon = Rounded.Stars,
        ) {
            onFavourite()
        }
        AppRoundIconButton(
            icon = Rounded.Search
        ) {
            onSearch()
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