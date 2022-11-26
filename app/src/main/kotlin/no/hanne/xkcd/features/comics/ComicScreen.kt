package no.hanne.xkcd.features.comics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import no.hanne.xkcd.core.models.xkcd.Comic
import no.hanne.xkcd.core.ui.theme.DarkBase
import no.hanne.xkcd.core.ui.theme.VeryTransparentLight

@Composable
fun ComicScreen(comic: Comic?) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
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
}
