package no.hanne.xkcd.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Stars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import no.hanne.xkcd.R
import no.hanne.xkcd.core.ui.components.AppRoundIconButton
import no.hanne.xkcd.core.ui.components.ErrorDialog
import no.hanne.xkcd.core.ui.components.PopUpDialog
import no.hanne.xkcd.core.ui.theme.GOLD
import no.hanne.xkcd.core.util.ShareUtils
import no.hanne.xkcd.navigation.Route
import no.hanne.xkcd.navigation.withParameters

@Composable fun HomeScreen(
    navController: NavController?,
    viewModel: HomeViewModel =
        hiltViewModel<HomeViewModelImpl>()
) {
    val context = LocalContext.current
    var showSearchPopup by remember { mutableStateOf(false) }
    var showMorePopup by remember { mutableStateOf(false) }

    LaunchedEffect("view-effects") {
        viewModel.viewEffect.collect { viewEffect: HomeViewEffect ->
            when (viewEffect) {
                is HomeViewEffect.NavigateToSearch -> {
                    navController?.navigate(
                        Route.Search.withParameters(
                            listOf(viewEffect.term, viewEffect.latest)
                        )
                    )
                }
            }
        }
    }
    ErrorDialog(
        errorMessageText = viewModel.errorMessage ?: "",
        dialogVisible = viewModel.errorMessage?.isNotEmpty() ?: false,
        onDialogDismissed = {
            viewModel.onErrorDialogDismissed()
        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            when (viewModel.isLoading) {
                false -> {
                    ComicArea(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(vertical = 16.dp),
                        comic = viewModel.comic,
                        explainLink = viewModel.explainLink
                    )
                }
                true -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .weight(1f)
                    )
                }
            }
            ControlsArea(
                modifier = Modifier.fillMaxWidth(),
                isFirstEnabled = viewModel.isFirstEnabled,
                isPreviousEnabled = viewModel.isPreviousEnabled,
                isNextEnabled = viewModel.isNextEnabled,
                isLastEnabled = viewModel.isLastEnabled,
                onFirst = viewModel::first,
                onPrevious = viewModel::previous,
                onNext = viewModel::next,
                onLast = viewModel::last,
                onMore = { showMorePopup = true }
            )
        }

        AppRoundIconButton(
            modifier = Modifier.size(50.dp).align(Alignment.TopEnd),
            icon = Icons.Rounded.Star,
            tint = if (viewModel.isFavourite) GOLD else Color.Gray,
            borderColor = Color.Transparent
        ) {
            viewModel.toggleFavourite()
        }
        PopUpDialog(
            modifier = Modifier
                .align(Alignment.BottomStart),
            showBackdrop = false,
            visible = viewModel.notifyNewComic,
            hideDialog = viewModel::hideNotifyNewComic
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 32.dp),
                text = stringResource(id = R.string.new_comic)
            )
        }

        PopUpDialog(
            modifier = Modifier
                .align(Alignment.BottomStart),
            visible = showMorePopup,
            showBackdrop = false,
            hideDialog = { showMorePopup = false }
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 32.dp),
                crossAxisSpacing = 16.dp,
                mainAxisSpacing = 16.dp,
                crossAxisAlignment = FlowCrossAxisAlignment.Center,
                mainAxisAlignment = FlowMainAxisAlignment.Center
            ) {
                AppRoundIconButton(
                    icon = Icons.Rounded.Shuffle
                ) {
                    viewModel.random()
                }
                AppRoundIconButton(
                    icon = Icons.Rounded.Stars
                ) {
                    navController?.navigate(Route.Favourites.destination)
                }
                AppRoundIconButton(
                    icon = Icons.Rounded.Search
                ) {
                    showSearchPopup = true
                }

                AppRoundIconButton(
                    icon = Icons.Rounded.Share
                ) {
                    ShareUtils.shareLinkToOthers(
                        context = context,
                        text = "https://xkcd.com/${viewModel.comic?.num}",
                        title = viewModel?.comic?.title ?: "XKCD Comic"
                    )
                }
            }
        }
        PopUpDialog(
            modifier = Modifier
                .align(Alignment.BottomStart),
            showBackdrop = false,
            visible = showSearchPopup,
            hideDialog = { showSearchPopup = false }
        ) {
            SearchControl(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 32.dp),
                onSearch = viewModel::onSearch
            )
        }
    }
}
