package no.hanne.xkcd.features.home

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import no.hanne.xkcd.R
import no.hanne.xkcd.core.models.xkcd.Comic
import no.hanne.xkcd.core.ui.theme.DarkBase
import no.hanne.xkcd.core.ui.theme.VeryTransparentLight

@Composable
fun ComicArea(
    modifier: Modifier = Modifier,
    comic: Comic?
) {
    val orientation = LocalConfiguration.current.orientation
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            text = "${comic?.num} - ${comic?.title}",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h2
        )
        val landscapeModifier = Modifier
            .fillMaxWidth(0.6f)
        val portraitModifier = Modifier
            .fillMaxWidth()
        CoilImage(
            imageModel = comic?.img,
            modifier = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                portraitModifier
            } else landscapeModifier,
            shimmerParams = ShimmerParams(
                baseColor = DarkBase,
                highlightColor = VeryTransparentLight,
                durationMillis = 950,
                dropOff = 0.65f,
                tilt = 20f
            ),
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.posted)
                .plus(" ${comic?.day}.${comic?.month}.${comic?.year}"), // think about formatting for next version
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.caption
        )
        Spacer(Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = comic?.transcript ?: "",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body1
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = comic?.alt ?: "",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body1
        )
    }
}
