package no.hanne.xkcd.features.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import no.hanne.xkcd.core.ui.components.AppRoundIconButton
import no.hanne.xkcd.core.ui.components.ErrorDialog

@Composable fun HomeScreen(
    navController: NavController?,
    viewModel: HomeViewModel =
        hiltViewModel<HomeViewModelImpl>()
) {
    val context = LocalContext.current
    LaunchedEffect("view-effects") {
        viewModel.viewEffect.collect { viewEffect: HomeViewEffect ->
           /* when (viewEffect) {
                is HomeViewEffect.NavigateToLogin -> {
                    navController?.navigate(Route.Login.destination)
                }
            }*/
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
                        comic = viewModel.comic
                    )
                }
                true -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
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
                onRandom = viewModel::random,
                onNext = viewModel::next,
                onLast = viewModel::last
            )
        }

        viewModel.explainLink?.let {
            AppRoundIconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                borderColor = Color.Transparent,
                icon = Rounded.Info
            ) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                ContextCompat.startActivity(context, browserIntent, null)
            }
        }
    }
}
