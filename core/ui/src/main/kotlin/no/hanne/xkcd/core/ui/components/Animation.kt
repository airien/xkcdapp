package no.hanne.xkcd.core.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import no.hanne.xkcd.core.ui.R

@Composable
fun Animation() {
    val composition by rememberLottieComposition(RawRes(R.raw.waves_of_pride))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f),
        composition = composition,
        progress = progress
    )
}
