package no.hanne.xkcd.features.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import no.hanne.xkcd.core.models.xkcd.Comic
import no.hanne.xkcd.core.ui.theme.DarkBase
import no.hanne.xkcd.core.ui.theme.VeryTransparentLight

@Composable
fun ComicArea(comic: Comic?, isLoading: Boolean) {
    Crossfade(targetState = isLoading) {
        when (it) {
            false -> {
                CoilImage(
                    imageModel = comic?.img,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shimmerParams = ShimmerParams(
                        baseColor = DarkBase,
                        highlightColor = VeryTransparentLight,
                        durationMillis = 950,
                        dropOff = 0.65f,
                        tilt = 20f
                    ),
                    contentScale = ContentScale.FillWidth
                )
            }
            true -> {
                CircularProgressIndicator()
            }
        }
    }
}
