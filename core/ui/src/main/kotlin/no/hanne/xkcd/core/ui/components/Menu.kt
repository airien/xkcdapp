package no.hanne.xkcd.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.hanne.xkcd.core.ui.R

@Composable
fun MenuItem(menuItem: MenuItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .testTag(menuItem.text)
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .height(20.dp)
                .aspectRatio(1f),
            painter = menuItem.iconPainter(),
            contentDescription = null
        )
        Text(
            menuItem.text,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Menu(menuItems: List<MenuItem>, onMenuClicked: (menuItem: MenuItem) -> Unit) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyRow(
                modifier = Modifier.testTag("list"),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(menuItems) { menuItem ->
                    MenuItem(menuItem = menuItem, onClick = { onMenuClicked(menuItem) })
                }
            }
        }
    }
}

sealed class MenuItem(@StringRes val textRes: Int?) {
    val text: String
        @Composable get() = if (textRes != null) stringResource(textRes) else ""

    @Composable
    abstract fun iconPainter(): Painter
}

class NavigationTarget(
    textRes: Int? = null,
    val route: String,
    val iconRes: Int,
) : MenuItem(textRes) {
    @Composable
    override fun iconPainter(): Painter = painterResource(id = iconRes)
}

@Preview(showSystemUi = true)
@Composable
fun PreviewMenu() {
    MaterialTheme {
        Menu(
            listOf<MenuItem>(
                NavigationTarget(
                    textRes = R.string.teststr,
                    route = "test2",
                    iconRes = R.drawable.ic_launcher_background
                ),

                NavigationTarget(
                    textRes = R.string.teststr,
                    route = "test1",
                    iconRes = R.drawable.ic_launcher_foreground
                )
            ),
            {}
        )
    }
}
