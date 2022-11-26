package no.hanne.xkcd.features.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import no.hanne.xkcd.components.ErrorDialog
import no.hanne.xkcd.features.comics.ComicScreen

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
        Crossfade(targetState = viewModel.isLoading) {
            when (it) {
                false -> {
                    ComicScreen(viewModel.comic)
                }
                true -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
