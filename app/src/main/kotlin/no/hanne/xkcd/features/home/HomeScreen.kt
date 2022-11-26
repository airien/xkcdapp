package no.hanne.xkcd.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import no.hanne.xkcd.core.ui.components.ErrorDialog

@Composable fun HomeScreen(
    navController: NavController?,
    viewModel: HomeViewModel =
        hiltViewModel<HomeViewModelImpl>()
) {
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
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            ComicArea(viewModel.comic, viewModel.isLoading)
            Spacer(Modifier.weight(1f))
            ControlsArea(
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
    }
}
